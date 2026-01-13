package com.daily.dose.service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.daily.dose.dto.VisitRequestDTO;
import com.daily.dose.dto.VisitResponseDTO;
import com.daily.dose.entity.PatientData;
import com.daily.dose.entity.VisitData;
import com.daily.dose.repository.PatientRepository;
import com.daily.dose.repository.StaffRepository;
import com.daily.dose.repository.VisitRepository;
import com.daily.dose.entity.Staff;
@Service
public class VisitService {
	private final StaffRepository staffRepository;
    private final PatientRepository patientRepository;
	private final VisitRepository visitRepository;
	@Autowired
	public VisitService(VisitRepository visitRepository, PatientRepository patientRepository,StaffRepository staffRepository) {
		this.visitRepository=visitRepository;
		this.staffRepository=staffRepository;
		this.patientRepository = patientRepository;
	}
	public List<VisitResponseDTO> findByStaffId(Long staffId) {
	    Staff staff=staffRepository.findById(staffId)
	        .orElseThrow(() -> new ResponseStatusException(
	            HttpStatus.NOT_FOUND, "Staff not found"));

	if (!staff.isStaffActive()) {
	    throw new ResponseStatusException(
	        HttpStatus.CONFLICT,
	        "Inactive staff has no active visits"
	    );
	}
	    return visitRepository.findByStaff_StaffId(staffId)
	            .stream()
	            .map(this::convertToResponseDTO)
	            .toList();
	}
	public List<VisitResponseDTO> findAllVisits() {
	    return visitRepository.findAll()
	            .stream()
	            .map(this::convertToResponseDTO)
	            .toList();
	}

	
	public VisitResponseDTO createVisit(VisitRequestDTO visitRequestDTO) {
		
		VisitData visit=new VisitData();
		PatientData patient=patientRepository.findById(visitRequestDTO.getPatientId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));
		Authentication auth =
			    SecurityContextHolder.getContext().getAuthentication();

			String staffEmail = auth.getName();

			Staff staff = staffRepository
			    .findByStaffEmail(staffEmail)
			    .orElseThrow(() -> new ResponseStatusException(
			        HttpStatus.NOT_FOUND, "Logged-in staff not found"));

		if (!staff.isStaffActive()) {
		    throw new ResponseStatusException(
		        HttpStatus.CONFLICT,
		        "Inactive staff cannot create visits"
		    );
		}

		
		if(patient.getPatientStatus()==false)
		{
			throw new ResponseStatusException(HttpStatus.CONFLICT,"The patient has been deactivated");
		}
		visit.setVisitTreatment(visitRequestDTO.getTreatmentNotes());
		visit.setStaff(staff);
		visit.setPatient(patient);
		visit.setVisitDate(visitRequestDTO.getVisitDate());
		return convertToResponseDTO(visitRepository.save(visit));
		
		
		
	}
	public Optional<VisitResponseDTO> findById(Long visitId){
		return visitRepository.findById(visitId).map(this::convertToResponseDTO);
	}
	public List<VisitResponseDTO> findByPatientId(Long patientId){
		PatientData patient =patientRepository.findById(patientId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient is not found"));
				return visitRepository.findAllByPatient_PatientId(patientId).stream().map(this::convertToResponseDTO).toList();
		
	}
	public VisitResponseDTO updateVisitNotes(VisitRequestDTO visitRequestDTO,long visitId) {
		VisitData visit=visitRepository.findById(visitId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Visit not found"));
		if (!visit.getPatient().getPatientStatus()) {
		    throw new ResponseStatusException(
		        HttpStatus.CONFLICT,
		        "Cannot update visit for inactive patient"
		    );
		}

		if (!visit.getStaff().isStaffActive()) {
		    throw new ResponseStatusException(
		        HttpStatus.CONFLICT,
		        "Cannot update visit by inactive staff"
		    );
		}

		visit.setVisitTreatment(visitRequestDTO.getTreatmentNotes());
		if(visitRequestDTO.getVisitDate()!=null)
			visit.setVisitDate(visitRequestDTO.getVisitDate());
		return convertToResponseDTO(visitRepository.save(visit));
	}
	
	public VisitResponseDTO convertToResponseDTO(VisitData visit) {
		return new VisitResponseDTO(
		visit.getVisitId(),
		visit.getVisitDate(),
		visit.getVisitTreatment(),
		visit.getPatient().getPatientId(),
		visit.getPatient().getPatientName(),
		visit.getStaff().getStaffId(),
		visit.getStaff().getStaffName(),
		visit.getCreatedAt(),
		visit.getUpdatedAt());
	}
	
	
}
