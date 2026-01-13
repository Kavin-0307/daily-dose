package com.daily.dose.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public class StaffRequestDTO {
	@NotBlank
	private String staffName;
	
	@Email
	@NotBlank
	private String staffEmail;
	
	@NotBlank
	private String password;
	
	public void setStaffName(String staffName) {
		this.staffName=staffName;
	}
	public void setStaffEmnail(String staffEmail) {
		this.staffEmail=staffEmail;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getStaffName() {
		return staffName;
	}
	public String getStaffEmail() {
		return staffEmail;
	}
	public String getPassword() {
		return password;
	}
}
