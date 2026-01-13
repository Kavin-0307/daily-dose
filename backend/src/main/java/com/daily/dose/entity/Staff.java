package com.daily.dose.entity;
import jakarta.persistence.*;
@Entity
@Table(name="staff")
public class Staff {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="staff_id")
	private long staffId;
	
	@Column(name="staff_name",nullable=false,length=100)
	private String staffName;
	@Column(name="staff_email",nullable=false,length=100,unique=true)
	private String staffEmail;
	@Column(name="role",nullable=false)
	private String staffRole;
	// BCrypt or similar hashed password (never store raw passwords)
	@Column(name="staff_password_hash",nullable=false,length=60)
	private String staffPasswordHash;
	@Column(name="staff_active",nullable=false)
	private boolean staffActive;
	
	public Staff() {}
	
	public String getStaffName() {
		return staffName;
	}
	public String getStaffEmail() {
		return staffEmail;
	}
	public String getStaffRole() {
		return staffRole;
	}
	public void setStaffRole(String staffRole) {
		this.staffRole=staffRole;
	}
	
	public long getStaffId() {
	    return staffId;
	}
	public void setPasswordHash(String staffPasswordHash) {
		this.staffPasswordHash=staffPasswordHash;
	}
	public String getStaffPasswordHash() {
		return staffPasswordHash;
	}
	public boolean isStaffActive() {
		return staffActive;
	}
	public void setStaffName(String staffName)
	{
		this.staffName=staffName;
	}
	public void setStaffEmail(String staffEmail)
	{
		this.staffEmail=staffEmail;
	}
	public void setStaffActive(boolean staffActive)
	{
		this.staffActive=staffActive;
	}
}
