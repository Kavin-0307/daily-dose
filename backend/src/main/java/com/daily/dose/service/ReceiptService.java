package com.daily.dose.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.openpdf.text.pdf.PdfName;
import com.daily.dose.repository.*;
import com.daily.dose.entity.*;
import org.openpdf.text.pdf.PdfString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfWriter;

import com.daily.dose.dto.ReceiptResponseDTO;
import com.daily.dose.dto.ReceiptUpdateDTO;
import com.daily.dose.dto.StaffResponseDTO;
import com.daily.dose.entity.PatientData;
import com.daily.dose.entity.Receipts;
import com.daily.dose.entity.Staff;
import com.daily.dose.entity.VisitData;
import com.daily.dose.repository.PatientRepository;
import com.daily.dose.repository.ReceiptRepository;
import com.daily.dose.repository.StaffRepository;
import com.daily.dose.repository.VisitRepository;
import com.daily.dose.dto.ReceiptRequestDTO;
/**
 * Receipt orchestration service.
 *
 * Handles receipt creation, validation, retrieval, and delegation
 * of PDF generation.
 *
 * Responsibilities:
 * - Enforce receipt-related business rules
 * - Coordinate between Patient, Staff, Visit, and Receipt domains
 * - Remain stateless and transaction-focused
 */

@Service
public class ReceiptService {

    private final PdfService pdfService;
	private final	ReceiptRepository receiptRepository;
	private final PatientRepository patientRepository;
	private final StaffRepository staffRepository;
	private final VisitRepository visitRepository;
	
	public ReceiptService(ReceiptRepository receiptRepository,StaffRepository staffRepository,PatientRepository patientRepository,VisitRepository visitRepository, PdfService pdfService) {
		this.patientRepository=patientRepository;
		this.receiptRepository=receiptRepository;
		this.staffRepository=staffRepository;
		this.visitRepository=visitRepository;
		this.pdfService = pdfService;
	}
	/**
	 * Creates a receipt with optional visit linkage.
	 *
	 * Invariants enforced:
	 * - Patient must exist and be active
	 * - Staff must exist
	 * - Visit (if provided) must belong to the patient
	 * - Only one receipt allowed per visit
	 */
	
	
	@Transactional
	public ReceiptResponseDTO createReceipt(ReceiptRequestDTO receiptRequestDTO){
		PatientData patient=patientRepository.findById(receiptRequestDTO.getPatientId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"patient not found"));
		if(patient.getPatientStatus()==false)
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		Staff staff=staffRepository.findById(receiptRequestDTO.getStaffId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"staff not found"));
		if (!staff.isStaffActive()) {
		    throw new ResponseStatusException(
		        HttpStatus.CONFLICT,
		        "Inactive staff cannot create receipts"
		    );
		}

		VisitData visit = null;

		if (receiptRequestDTO.getVisitId() != null) {
		    visit = visitRepository.findById(receiptRequestDTO.getVisitId())
		            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "visit not found"));

		    if (visit.getPatient().getPatientId()!=(patient.getPatientId())) {
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong patient");
		    }
		    if (!visit.getPatient().getPatientStatus()) {
		        throw new ResponseStatusException(
		            HttpStatus.CONFLICT,
		            "Cannot create receipt for inactive patient visit"
		        );
		    }

		    if (receiptRepository.findByVisit_VisitId(visit.getVisitId()).isPresent()) {
		        throw new ResponseStatusException(HttpStatus.CONFLICT, "Receipt already exists for visit");
		    }
		}

		
		
		
		String receiptNumber=generateReceiptNumber();
		
		Receipts receipts =new Receipts();
		receipts.setReceiptNumber(receiptNumber);
		receipts.setReceiptAmount(receiptRequestDTO.getReceiptAmount());
		receipts.setDescription(receiptRequestDTO.getReceiptDescription());
		receipts.setReceiptPaymentMethod(receiptRequestDTO.getReceiptPaymentMethod());
		receipts.setReceiptPaid(true);
		receipts.setStaff(staff);
		receipts.setPatient(patient);
		receipts.setVisit(visit);
		return convertToResponseDTO(receiptRepository.save(receipts));
		
	}
	public byte[] generatePDF(long receiptId) {
	    Receipts receipt = receiptRepository.findById(receiptId)
	        .orElseThrow(() -> new ResponseStatusException(
	            HttpStatus.NOT_FOUND, "Receipt not found"));

	    Path pdfPath = pdfService.generateReceiptPDF(receipt);

	    try {
	        return Files.readAllBytes(pdfPath);
	    } catch (IOException e) {
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR,
	            "Failed to read generated PDF",
	            e
	        );
	    }
	}

	/**
	 * Fetch receipt by primary key.
	 * Returns Optional to allow controller-level response decisions.
	 */
	public Optional<ReceiptResponseDTO> findById(Long receiptId) {
		return receiptRepository.findById(receiptId).map(this::convertToResponseDTO);
	}
	/**
	 * Fetch all receipts belonging to a patient.
	 * Patient existence is validated explicitly.
	 */
	public List<ReceiptResponseDTO> findByPatient(Long patientId){
		patientRepository.findById(patientId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		return receiptRepository.findAllByPatient_PatientId(patientId).stream().map(this::convertToResponseDTO).toList();
	}
	public ReceiptResponseDTO getReceiptByVisit(Long visitId) {
	    VisitData visit = visitRepository.findById(visitId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found"));

	    Receipts receipt = receiptRepository.findByVisit_VisitId(visitId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt not found"));

	    return convertToResponseDTO(receipt);
	}
	/**
	 * Generates a sequential, year-scoped receipt number.
	 *
	 * Format: DD-YYYY-XXXXXX
	 * Example: DD-2026-000042
	 */
	private String generateReceiptNumber() {
		int year = LocalDate.now().getYear();
		String prefix = "DD-" + year + "-";
		long countForYear = receiptRepository.countForYearWithLock(prefix);
		long next = countForYear + 1;
		String receiptNumber = prefix + String.format("%06d", next);
		return receiptNumber;
		
	}

	public List<ReceiptResponseDTO> getAllReceipts() {
        return receiptRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
	}
	
	@Transactional
	public ReceiptResponseDTO updateReceipt(
	        Long receiptId,
	        ReceiptUpdateDTO dto
	) {
	    Receipts receipt = receiptRepository.findById(receiptId)
	            .orElseThrow(() ->
	                new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt not found"));

	    if (!receipt.getStaff().isStaffActive()) {
	        throw new ResponseStatusException(
	            HttpStatus.CONFLICT, "Inactive staff cannot modify receipt");
	    }

	    if (receipt.isReceiptPaid() && Boolean.FALSE.equals(dto.getReceiptPaid())) {
	        throw new ResponseStatusException(
	            HttpStatus.CONFLICT, "Paid receipt cannot be reverted");
	    }

	    if (dto.getReceiptDescription() != null) {
	        receipt.setDescription(dto.getReceiptDescription());
	    }

	    if (dto.getReceiptPaymentMethod() != null) {
	        receipt.setReceiptPaymentMethod(dto.getReceiptPaymentMethod());
	    }

	    if (dto.getReceiptPaid()!=null  && !receipt.isReceiptPaid()) {
	        receipt.setReceiptPaid(dto.getReceiptPaid());
	    }

	    return convertToResponseDTO(receiptRepository.save(receipt));
	}

	public ReceiptResponseDTO convertToResponseDTO(Receipts receipts) {
		return new ReceiptResponseDTO(
				receipts.getReceiptId(),
				receipts.getReceiptNumber(),
				receipts.getPatient().getPatientId(),
				receipts.getPatient().getPatientName(),
				receipts.getStaff().getStaffId(),
				receipts.getStaff().getStaffName(),
				receipts.getReceiptAmount(),
				receipts.getReceiptDescription(),
				receipts.getReceiptPaymentMethod(),
				receipts.isReceiptPaid(),
				receipts.getCreatedAt()
				);
	}
}
