package com.daily.dose.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.daily.dose.dto.StaffRequestDTO;
import com.daily.dose.dto.StaffResponseDTO;
import com.daily.dose.entity.Staff;
import com.daily.dose.repository.StaffRepository;

@Service
public class StaffService {
	private final StaffRepository staffRepository;
	// Password hashing is handled at service layer (never in entity)
	private final PasswordEncoder passwordEncoder;
	public StaffService(StaffRepository staffRepository,PasswordEncoder passwordEncoder) {
		this.staffRepository=staffRepository;
		this.passwordEncoder=passwordEncoder;
	}
	/**
	 * Creates a new staff account.
	 *
	 * Invariants enforced:
	 * - Staff email must be unique
	 * - Password is stored as a hash only
	 * - Staff is active by default on creation
	 */
	public StaffResponseDTO createStaff(StaffRequestDTO staffRequestDTO) {

	    Staff current = getCurrentStaff();

	    if (!"ADMIN".equals(current.getStaffRole())) {
	        throw new ResponseStatusException(
	            HttpStatus.FORBIDDEN,
	            "Only admins can create staff"
	        );
	    }

	    if (staffRepository.findByStaffEmail(staffRequestDTO.getStaffEmail()).isPresent()) {
	        throw new ResponseStatusException(
	            HttpStatus.CONFLICT,
	            "Staff email already exists"
	        );
	    }

	    Staff staff = new Staff();
	    staff.setStaffName(staffRequestDTO.getStaffName());
	    staff.setStaffEmail(staffRequestDTO.getStaffEmail());
	    staff.setPasswordHash(passwordEncoder.encode(staffRequestDTO.getPassword()));
	    staff.setStaffActive(true);

	    // 🔐 ROLE ASSIGNMENT
	    staff.setStaffRole(
	        staffRepository.count() == 0 ? "ADMIN" : "STAFF"
	    );

	    return convertToResponseDTO(staffRepository.save(staff));
	}

	/*	long staffCount = staffRepository.count();

		Authentication auth = SecurityContextHolder
		        .getContext()
		        .getAuthentication();

		boolean isAuthenticated =
		        auth != null &&
		        auth.isAuthenticated() &&
		        !(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails);

		if (staffCount > 0 && !isAuthenticated) {
		    throw new ResponseStatusException(
		        HttpStatus.FORBIDDEN,
		        "Authentication required to create staff"
		    );
		}
*/

	private Staff getCurrentStaff() {
	    Authentication auth =
	        SecurityContextHolder.getContext().getAuthentication();

	    String email = auth.getName();

	    return staffRepository.findByStaffEmail(email)
	        .orElseThrow(() ->
	            new ResponseStatusException(
	                HttpStatus.UNAUTHORIZED,
	                "Current staff not found"
	            )
	        );
	}

	/**
	 * Fetch staff details by ID.
	 * Throws 404 if staff does not exist.
	 */
	public StaffResponseDTO getByID(Long id) {
		Staff staff=staffRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Staff not found"));
		return convertToResponseDTO(staff);
	}
	/**
	 * Returns only active staff members.
	 * Inactive accounts are intentionally hidden.
	 */
	public List<StaffResponseDTO> getAllActive() {
        return staffRepository.findAll()
                .stream()
                .filter(Staff::isStaffActive)
                .map(this::convertToResponseDTO)
                .toList();
    }
	/**
	 * Soft-deactivates a staff account.
	 * Used instead of hard deletion to preserve audit integrity.
	 */
	public void deactivate(long id) {

	    Staff current = getCurrentStaff();

	    if (!"ADMIN".equals(current.getStaffRole())) {
	        throw new ResponseStatusException(
	            HttpStatus.FORBIDDEN,
	            "Only admins can deactivate staff"
	        );
	    }

	    Staff staff = staffRepository.findById(id)
	        .orElseThrow(() ->
	            new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Staff not found"
	            )
	        );

	    // ❌ self-deactivation
	    if (staff.getStaffEmail().equals(current.getStaffEmail())) {
	        throw new ResponseStatusException(
	            HttpStatus.CONFLICT,
	            "You cannot deactivate yourself"
	        );
	    }

	    // ❌ last admin
	    if ("ADMIN".equals(staff.getStaffRole())) {
	        long activeAdmins =
	            staffRepository.countByStaffRoleAndStaffActive("ADMIN", true);

	        if (activeAdmins <= 1) {
	            throw new ResponseStatusException(
	                HttpStatus.CONFLICT,
	                "At least one active admin must exist"
	            );
	        }
	    }

	    staff.setStaffActive(false);
	    staffRepository.save(staff);
	}


	private StaffResponseDTO convertToResponseDTO(Staff staff) {
		return new StaffResponseDTO(
			    staff.getStaffId(),
			    staff.getStaffName(),
			    staff.getStaffEmail(),
			    staff.getStaffRole(),
			    staff.isStaffActive()
			);

	}




}
