package com.daily.dose.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.daily.dose.dto.PatientResponseDTO;
import com.daily.dose.dto.StaffResponseDTO;
import com.daily.dose.dto.PatientRequestDTO;
import com.daily.dose.service.PatientService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api")

@PreAuthorize("isAuthenticated()")
@CrossOrigin()
public class PatientController {
	Logger logger=LoggerFactory.getLogger(PatientController.class);
	
	private final PatientService service;
	@Autowired 
	public PatientController(PatientService service) {
		this.service=service;
	}
	
	@PostMapping("/patients")
	
	public ResponseEntity<PatientResponseDTO> createPatient(@Valid@RequestBody PatientRequestDTO patientRequestDTO)
	{
		//takes a PatientRequestDTO and calls the service.create to save the enetry. It return 201 created with saved patient entry
		logger.info("Creating a new patient entry");
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createPatient(patientRequestDTO));
	}
	
	@PutMapping(value="/patients/{id}")
	public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id")Long id,@Valid@RequestBody PatientRequestDTO patientRequestDTO){
		logger.info("Updating the patient's info by id");
		return ResponseEntity.ok(service.updatePatient(patientRequestDTO,id));
	}
	 @GetMapping("/patients")
	    public ResponseEntity<List<PatientResponseDTO>> getAllStaff() {
	        return ResponseEntity.ok(service.getAllPatients());
	    }
	@GetMapping("/patients/{id}")
	public ResponseEntity<PatientResponseDTO> findPatientById(@PathVariable("id") Long id)
	{
		//Path variable extracts the id calue from the url , calls service find by id.
		logger.info("Fetching patients by id");
		return ResponseEntity.of(service.findById(id));
	}
	@GetMapping("/staff/{staffId}/patients")
	public ResponseEntity<List<PatientResponseDTO>> getPatientsForStaff(
	        @PathVariable Long staffId
	) {
	    return ResponseEntity.ok(
	        service.getPatientsByStaff(staffId)
	    );
	}

	
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable("id")Long id){
		logger.info("Delete the patient by id");
		service.deactivatePatient(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}	
