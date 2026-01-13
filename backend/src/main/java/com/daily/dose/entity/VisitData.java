package com.daily.dose.entity;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
/*The file basically creates a table for the patient's visit data, It stores the visit Id which is that specific visit's id
 * The patient table is linked to this table
 * then it also links the staff that is seeing the patient
 * it stores the treatment that was allotted at that time
 * it also stores the recent most updation of the row
 * it also stores when the patient was visited
 * it acts as a translational layer between patientdata and the visits (events)
 * */
import jakarta.persistence.*;
@Entity
@Table(name="visit_data")
public class VisitData {
	/*this is the table that i use store everything related to the patients' visits like their actual symptoms that day , what day they  visited,The treating physiotherapist name,the treatment prescribed,
	and any  other such data related to the patients visits*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long visitId;
	@ManyToOne(optional=false)
	@JoinColumn(name="patient_id")
	private PatientData patient;
	@Column(name="visit_treatment",nullable=false)
	private String visitTreatment;//Basically stores whatever treatment was prescribed that time
	@ManyToOne(optional=false)
	@JoinColumn(name="staff_id")
	private Staff staff;//Stores the name of physiotherapist that actually saw the patient
	@CreationTimestamp//Stores when the thing was created basically
	@Column(nullable=false,updatable=false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(nullable=false)
	private LocalDateTime updatedAt;
	@Column(nullable=false)
	private LocalDateTime visitDate;
	
	public VisitData() {}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public PatientData getPatient() {
		return patient;
	}
	public String getVisitTreatment() {
		return visitTreatment;
	}
	public Staff getStaff() {
		return staff;//IG? This is just added rn,will see later
		
	}
	public long getVisitId() {
		return visitId;
	}
	public void setVisitTreatment(String visitTreatment)
	{
		this.visitTreatment=visitTreatment;
	}
	public void setStaff(Staff staff) {
		this.staff=staff;
	}
	public void setPatient(PatientData patient) {
		this.patient=patient;
	}
	public void setVisitDate(LocalDateTime visitDate) {
		this.visitDate=visitDate;
	}
	public LocalDateTime getVisitDate() {
		return visitDate;
	}
	
	
	

}
