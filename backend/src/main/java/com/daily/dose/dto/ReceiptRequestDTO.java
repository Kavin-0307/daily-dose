package com.daily.dose.dto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public class ReceiptRequestDTO {
	@NotNull
	@Positive
	private Long patientId;
	
	@NotNull
	@Positive
	private Long staffId;
	private Long visitId;
	
	@NotNull
	@Positive
	private BigDecimal receiptAmount;
	@NotBlank
	private String receiptDescription;
	@NotBlank
	private String receiptPaymentMethod;
	
	private boolean paid;
	public boolean isPaid() {
		return paid;
	}
	public Long getPatientId() {
		return patientId;
	}
	public Long getStaffId() {
		return staffId;
	}
	public Long getVisitId() {
		return visitId;
	}
	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}
	public String getReceiptDescription() {
		return receiptDescription;
	}
	public String getReceiptPaymentMethod() {
		return receiptPaymentMethod;
	}
	public void setPatientId(long patientId) {
		this.patientId=patientId;
	}
	public void setStaffId(long staffId) {
		this.staffId=staffId;
	}
	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount =receiptAmount;
	}
	public void setReceiptDescription(String receiptDescription) {
		this.receiptDescription=receiptDescription;
	}
	public void setRecieptPaymentMethod(String receiptPaymentMethod) {
		this.receiptPaymentMethod=receiptPaymentMethod;
	}
	
	
}
