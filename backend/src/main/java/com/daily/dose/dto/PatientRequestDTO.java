package com.daily.dose.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {
	

			/*It acts as a data transfer object written specifically to register new user*/
			@NotBlank
			@Size(max=90,message="Patient Name must be within 90 characters")
			private String patientName;
			
			@NotBlank
			private String patientIssue;
			@NotBlank
			private String patientAge;
			@NotBlank
			private String patientGender;
			@NotNull
			@Positive
			private Double patientHeight;
			@NotNull
			@Positive
			private Double patientWeight;
			@NotBlank
			private String patientPhone;
			@NotBlank
			private String patientAddress;
			
			private boolean patientStatus;
			@NotBlank
			private String patientEmail;
			
			private String patientDob;
			
			private String patientAdhaar;
			
			
			
			public String getPatientEmail() {
				return patientEmail;
			}
			public String getPatientDob(){
				return patientDob;
			}
			public String getPatientAdhaar() {
				return patientAdhaar;
			}
			public void setPatientEmail(String patientEmail) {
				this.patientEmail=patientEmail;
			}
			public void setPatientDob(String patientDob) {
				this.patientDob=patientDob;
			}
			public void setPatientAdhaar(String patientAdhaar)
			{
				this.patientAdhaar=patientAdhaar;
			}
			
			public boolean getPatientStatus() {
				return patientStatus;
			}
			public void setPatientStatus(boolean patientStatus) {
				this.patientStatus=patientStatus;
			}
			
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
