package com.daily.dose.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.daily.dose.repository.*;

import jakarta.transaction.Transactional;

import com.daily.dose.dto.PatientRequestDTO;
import com.daily.dose.dto.PatientResponseDTO;
import com.daily.dose.dto.StaffResponseDTO;
import com.daily.dose.entity.PatientData;
import com.daily.dose.entity.Staff;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class PatientService {
	private final PatientRepository patientRepository;
	private final StaffRepository staffRepository;

	public PatientService(PatientRepository patientRepository,StaffRepository staffRepository) {
		this.patientRepository=patientRepository;
		this.staffRepository=staffRepository;
		
	}
	@Transactional
	public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
		
		PatientData patient=new PatientData();
		//Set all the field for the patient 
		patient.setPatientName(patientRequestDTO.getPatientName());
		patient.setPatientAddress(patientRequestDTO.getPatientAddress());
		patient.setPatientWeight(patientRequestDTO.getPatientWeight());
		patient.setPatientHeight(patientRequestDTO.getPatientHeight());
		patient.setPatientIssue(patientRequestDTO.getPatientIssue());
		patient.setPatientPhone(patientRequestDTO.getPatientPhone());
		patient.setPatientAge(patientRequestDTO.getPatientAge());
		patient.setPatientGender(patientRequestDTO.getPatientGender());
		patient.setPatientNumber(generatePatientNumber());
		patient.setPatientStatus(true);
		patient.setPatientEmail(patientRequestDTO.getPatientEmail());
		patient.setPatientDob(patientRequestDTO.getPatientDob());
		patient.setPatientAdhaar(patientRequestDTO.getPatientAdhaar());

		return convertToResponseDTO(patientRepository.save(patient));
		
	}
	
	public PatientResponseDTO updatePatient(PatientRequestDTO patientRequestDTO,Long id) {
		PatientData patient=patientRepository.findById(id).orElseThrow(()->new ResponseStatusException (HttpStatus.NOT_FOUND,"Patient Not Found"));
		if (!patient.getPatientStatus()) {
		    throw new ResponseStatusException(
		        HttpStatus.CONFLICT,
		        "Cannot update inactive patient"
		    );
		}
		

		patient.setPatientAddress(patientRequestDTO.getPatientAddress());
		patient.setPatientAge(patientRequestDTO.getPatientAge());
		patient.setPatientGender(patientRequestDTO.getPatientGender());
		patient.setPatientHeight(patientRequestDTO.getPatientHeight());
		patient.setPatientIssue(patientRequestDTO.getPatientIssue());
		patient.setPatientName(patientRequestDTO.getPatientName());
		patient.setPatientPhone(patientRequestDTO.getPatientPhone());
		patient.setPatientWeight(patientRequestDTO.getPatientWeight());
		patient.setPatientEmail(patientRequestDTO.getPatientEmail());
		patient.setPatientDob(patientRequestDTO.getPatientDob());
		patient.setPatientAdhaar(patientRequestDTO.getPatientAdhaar());

		return convertToResponseDTO(patientRepository.save(patient));
	}
	public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .filter(PatientData::getPatientStatus)
                .map(this::convertToResponseDTO)
                .toList();
    }
	private String generatePatientNumber() {
	    int year = LocalDate.now().getYear();
	    String prefix = "DD-PAT-" + year + "-";

	    long count = patientRepository.countForYearWithLock(prefix);
	    long next = count + 1;

	    return prefix + String.format("%06d", next);
	}

	
	public void deactivatePatient(Long id) {
		PatientData patient=patientRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient Not Found"));
		patient.setPatientStatus(false);
		
		patientRepository.save(patient);
	}
	public Optional<PatientResponseDTO> findById(Long patientId){
		return patientRepository.findById(patientId).map(this::convertToResponseDTO);
	}
	public List<PatientResponseDTO> getPatientsByStaff(Long staffIdFromPath) {

	    Authentication auth =
	        SecurityContextHolder.getContext().getAuthentication();

	    String email = auth.getName(); // JWT subject
	    Staff loggedInStaff = staffRepository
	        .findByStaffEmail(email)
	        .orElseThrow(() -> new ResponseStatusException(
	            HttpStatus.FORBIDDEN, "Staff not found"));

	    Long effectiveStaffId;

	    if ("ADMIN".equals(loggedInStaff.getStaffRole())) {
	        // ADMIN can view any staff
	        effectiveStaffId = staffIdFromPath;
	    } else {
	        // STAFF can ONLY view their own patients
	        effectiveStaffId = loggedInStaff.getStaffId();
	    }

	    return patientRepository.findPatientsByStaffId(effectiveStaffId)
	            .stream()
	            .map(this::convertToResponseDTO)
	            .toList();
	}


	
	public PatientResponseDTO convertToResponseDTO(PatientData patient) {
		return new PatientResponseDTO(
				patient.getPatientId(),
				patient.getPatientNumber(),
				patient.getPatientName(),
				patient.getPatientAge(),
				patient.getPatientGender(),				
				patient.getPatientHeight(),
				patient.getPatientWeight(),
				patient.getPatientPhone(),
				patient.getPatientAddress(),
				patient.getPatientIssue(),
				patient.getPatientStatus(),
				
				patient.getPatientAdhaar(),
				patient.getPatientDob(),
				patient.getPatientEmail());
		
	}
}
