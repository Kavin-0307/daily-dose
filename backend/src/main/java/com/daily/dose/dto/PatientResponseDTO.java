package com.daily.dose.dto;

public record PatientResponseDTO(
		Long id,
		String patientNumber,
		String patientName,
		String patientAge,
		String patientGender,
		double patientHeight,
		double patientWeight,
		String patientPhone,
		String patientAddress,
		String patientIssue,
		boolean patientStatus,
		String patientAdhaar,
		String patientDob,
		String patientEmail
		
		){
	
}
