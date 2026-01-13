package com.daily.dose.entity;
import jakarta.persistence.*;
@Entity
@Table(name="patient_data")
/*The folowing file is used to store the Patient Data.
 * Currently the fields being stored are:-
 * patient Name
 * patient Height
 * patient Weight
 * Patient id
 * patient phone number
 * Disease name affecting the patient.
 * patient address
 * patient gender
 * patient status 
 * patient adhaar
 * patient DOB
 * This layer actrs as the layer for the patient part of the architecture.
 * THen normal getters and setters are used as and when required.
 * */
public class PatientData {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private long patientId;
		
		@Column(name="patient_name",nullable=false,length=100)
		private String patientName;//Declaring the column for name of the patient
		
		@Column(name="patient_height",nullable=false)
		private double patientHeight;
		
		@Column(name="patient_weight",nullable=false)
		private double patientWeight;//It stores the weight of the patient useful for designing workouts later on
		
		@Column(name="patient_issue",nullable=false,length=500)
		private String patientIssue;//This is meant to store the exact problem faced by the patient for future access
		
		@Column(name="patient_phone",nullable=false,length=15)
		private String patientPhone;//Stores the phone number needs to be enforced by formatting
		
		@Column(name="patient_address",nullable=false)
		private String patientAddress;
		
		@Column(name="patient_age",nullable=false)
		private String patientAge;
		@Column(name="patient_gender",nullable=false)
		private String patientGender;
		
		//patientEmail , patientAdhaar,patientDOB
		@Column(name="patient_number",nullable=false,unique=true)
		private String patientNumber;
		@Column(name="patient_email",nullable=false)
		private String patientEmail;
		@Column(name="patient_status",nullable=false)
		private boolean patientStatus=true;
		
		@Column(name="patient_adhaar",nullable=false)
		private String patientAdhaar;
		
		@Column(name="patient_dob",nullable=false)
		private String patientDob;
		
		public PatientData() {}
		public long getPatientId() {
			return patientId;
			
		}
		public String getPatientEmail() {
			return patientEmail;
		}
		public String getPatientAdhaar() {
			return patientAdhaar;
		}
		public String getPatientDob() {
			return patientDob;
		}
		public String getPatientAddress() {
			return patientAddress;
		}
		
		public String getPatientName() {
			return patientName;
		}
		public String getPatientNumber() {
			return patientNumber;
		}
		public double getPatientHeight()
		{
			return patientHeight;
		}
		public double getPatientWeight() {
			return patientWeight;//Self-explanatory
		}
		public String getPatientIssue() {
			return patientIssue;//Only the main heading of their issue is stored using this not their appointment by appointment issues.
		}
		public String getPatientPhone() {
			return patientPhone;//Leaving the validation for the service
		}
		public String getPatientAge()
		{
			return patientAge;
		}
		public String getPatientGender() {
			return patientGender;
		}
		public void  setPatientName(String patientName) {
			this.patientName=patientName;
		}
		public void setPatientHeight(double patientHeight) {
			this.patientHeight=patientHeight;
		}
		public void setPatientWeight(double patientWeight) {
			this.patientWeight=patientWeight;
		}
		public void setPatientIssue(String patientIssue)
		{
			this.patientIssue=patientIssue;
		}
		public void setPatientPhone(String patientPhone) {
			this.patientPhone=patientPhone;
		}
		public void setPatientAddress(String patientAddress) {
			this.patientAddress=patientAddress;
		}
		public void setPatientGender(String patientGender) {
			
			this.patientGender=patientGender;
		}
		public void setPatientAge(String patientAge) {
			this.patientAge=patientAge;
		}
		public void setPatientStatus(boolean patientStatus) {
			this.patientStatus=patientStatus;
		}
		public boolean getPatientStatus() {
			return patientStatus;
		}
		public void setPatientEmail(String patientEmail) {
			this.patientEmail=patientEmail;
		}
		public void setPatientDob(String patientDob) {
			this.patientDob=patientDob;
			
		}
		public void setPatientNumber(String patientNumber) {
			this.patientNumber=patientNumber;
		}
		public void setPatientAdhaar(String patientAdhaar) {
			this.patientAdhaar=patientAdhaar;
		}
}
/*the above file is a persistence enitity that represents the core information of the patients and it enables storage of patients and their attributes in table format*/