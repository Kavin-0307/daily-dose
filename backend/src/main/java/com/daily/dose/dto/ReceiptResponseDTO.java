package com.daily.dose.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReceiptResponseDTO(
		Long receiptId,
		String receiptNumber,
		long patientId,
		String patientName,
		long staffId,
		String staffName,
		BigDecimal amount,
		String description,
		String receiptPaymentMethod,
		boolean paid,
		LocalDateTime createdAt
		) {

}
