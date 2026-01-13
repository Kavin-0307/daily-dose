package com.daily.dose.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
/*Financial transaction entity that represents the billing record for a given patient*/


@Entity
@Table(name="receipts",
		indexes= {
				//Ensures the receipt numbers remain globally unique and query efficient
				@Index(columnList="receipt_number",unique=true)
				
		}
)
public class Receipts {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="receipt_id")
	private long receiptId;
	
	@Column(name="receipt_number",nullable=false,length=20,unique=true)
	private String receiptNumber;
	
	@Column(name="receipt_amount",nullable=false)
	private BigDecimal receiptAmount;
	// Line-item or summary description of the billed servic
	@Column(name="receipt_description",nullable=false)
	private String receiptDescription;
	
	@Column(name="receipt_paid",nullable=false)
	private boolean receiptPaid;
	@CreationTimestamp
	@Column(nullable=false)
	private LocalDateTime createdAt;
	
	
	@Column(name="payment_method",nullable=false)
	private String receiptPaymentMethod;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="patient_id",nullable=false)
	private PatientData patient;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="staff_id",nullable=false)
	private Staff staff;
	
	@OneToOne
	@JoinColumn(name = "visit_id", unique = true)
	private VisitData visit;

	public Receipts() {}
	public long getReceiptId()
	{
		return receiptId;
	}
	
	public String getReceiptNumber() {
		return receiptNumber;
	}
	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}
	public String getReceiptDescription() {
		return receiptDescription;
	}
	public boolean isReceiptPaid() {
		return receiptPaid;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public String getReceiptPaymentMethod() {
		return receiptPaymentMethod;
	}
	public PatientData getPatient() {
		return patient;
	}
	public Staff getStaff() {
		return staff;
	}
	public VisitData getVisit() {
		return visit;
	}
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber=receiptNumber;
	}
	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount=receiptAmount;
	}
	public void setDescription(String receiptDescription) {
		this.receiptDescription=receiptDescription;
	}
	public void setReceiptPaymentMethod(String receiptPaymentMethod) {
		this.receiptPaymentMethod=receiptPaymentMethod;
	}
	public void setReceiptPaid(boolean receiptPaid) {
		this.receiptPaid=receiptPaid;
	}
	public void setPatient(PatientData patient) {
		this.patient=patient;
	}
	public void setStaff(Staff staff) {
		this.staff=staff;
	}
	public void setVisit(VisitData visit) {
		this.visit=visit;
	}

	
		
}
