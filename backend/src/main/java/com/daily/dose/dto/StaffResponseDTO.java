package com.daily.dose.dto;

public record StaffResponseDTO(
	    Long staffId,
	    String staffName,
	    String staffEmail,
	    String staffRole,
	    boolean staffActive
	) {}
