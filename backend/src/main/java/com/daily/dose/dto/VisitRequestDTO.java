package com.daily.dose.dto;

import java.time.LocalDateTime;

import com.daily.dose.entity.Staff;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
public class VisitRequestDTO {
	@NotNull
	@Positive
	private Long patientId;
	@NotNull
	@Positive
	private Long staffId;
	@NotNull
	private  LocalDateTime visitDate;
	@NotBlank
	@Size(max=4000)
	private String treatmentNotes;
	
	private Staff staff;
	public void setTreatmentNotes(String treatmentNotes) {
		this.treatmentNotes=treatmentNotes;
	}
	public void setPatientId(long patientId) {
		this.patientId=patientId;
	}
	public void setStaffId(long staffId) {
		this.staffId=staffId;
	}
	public void setVisitDate(LocalDateTime visitDate) {
		this.visitDate=visitDate;
	}
	public String getTreatmentNotes() {
		return treatmentNotes;
	}
	public LocalDateTime getVisitDate() {
		return visitDate;
	}
	public long getStaffId() {
		return staffId;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setStaff(Staff staff) {
		this.staff=staff;
	}
	public Staff getStaff()
	{
		return staff;
	}
	
}
