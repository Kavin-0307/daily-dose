package com.daily.dose.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daily.dose.dto.VisitRequestDTO;
import com.daily.dose.dto.VisitResponseDTO;
import com.daily.dose.service.VisitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")

@PreAuthorize("isAuthenticated()")
@CrossOrigin()

public class VisitController {
	Logger logger=LoggerFactory.getLogger(VisitController.class);
	
	private final VisitService service;
	@Autowired
	public VisitController(VisitService service) {
		this.service=service;
	}
	
	@PostMapping("/visits")
	public ResponseEntity<VisitResponseDTO> createVisit(@Valid@RequestBody VisitRequestDTO visitRequestDTO){
		logger.info("Creating a new visit");
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createVisit(visitRequestDTO));
	}
	@GetMapping("/visits/{id}")
	public ResponseEntity<VisitResponseDTO> findById(@PathVariable("id") Long id){
		logger.info("Fetching visit by id");
		return ResponseEntity.of(service.findById(id));
	}
	@GetMapping("/visits")
	public ResponseEntity<List<VisitResponseDTO>> getAllVisits() {
	    return ResponseEntity.ok(service.findAllVisits());
	}
	@GetMapping("/staff/{staffId}/visits")
	public ResponseEntity<List<VisitResponseDTO>> getVisitsByStaff(
	        @PathVariable Long staffId) {
	    return ResponseEntity.ok(service.findByStaffId(staffId));
	}

	@GetMapping("/patients/{patientId}/visits")
	public ResponseEntity<List<VisitResponseDTO>> findAllVisits(@PathVariable("patientId") Long patientId){
		return ResponseEntity.ok(service.findByPatientId(patientId));
	}
	@PutMapping(value="/visits/{id}")
	public ResponseEntity<VisitResponseDTO> updateNotes(@PathVariable("id") Long id,@Valid@RequestBody VisitRequestDTO visitRequestDTO)
	{
		return ResponseEntity.ok(service.updateVisitNotes(visitRequestDTO, id));
	}

}
