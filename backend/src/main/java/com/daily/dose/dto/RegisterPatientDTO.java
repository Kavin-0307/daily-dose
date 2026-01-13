package com.daily.dose.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterPatientDTO {
	/*It acts as a data transfer object written specifically to register new user*/
	@NotBlank
	@Size(max=90,message="Patient Name must be within 90 characters")
	private String patientName;
	
	@NotBlank
	private String patientIssue;
	private String patientAge;
	private String patientGender;
	private double patientHeight;
	
	private double patientWeight;
	@NotBlank
	private String patientPhone;
	@NotBlank
	private String patientAddress;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName=patientName;
	}
	
	
	
	public void setPatientIssue(String patientIssue) {
		this.patientIssue=patientIssue;
	}
	public String getPatientIssue() {
		return patientIssue;
	}
	
	
	
	public void setPatientWeight(double patientWeight) {
		this.patientWeight=patientWeight;
	}
	public double getPatientWeight() {
		return patientWeight;
	}
	
	
	
	public void setPatientHeight(double patientHeight) {
		this.patientHeight=patientHeight;
	}
	public double getPatientHeight() {
		return patientHeight;
	}
	
	public void setPatientPhone(String patientPhone) {
		this.patientPhone=patientPhone;
	}
	public String getPatientPhone() {
		return patientPhone;
	}
	
	
	public String getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(String patientAge) {
		this.patientAge=patientAge;
	}
	
	
	
	public String getPatientGender() {
		return patientGender;
	}
	public void setPatientGender(String patientGender) {
		this.patientGender=patientGender;
	}
	
	
	public void  setPatientAddress(String patientAddress)
	{
		this.patientAddress=patientAddress;
	}
	public String getPatientAddress() {
		return patientAddress;
	}

}
