package com.daily.dose.dto;

import java.time.LocalDateTime;

public record VisitResponseDTO(
		Long visitId,
		LocalDateTime visitDate,
		String treatmentNotes,
		long patientId,
		String patientName,
		long staffId,
		String staffName,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
		
		) {
	
}
