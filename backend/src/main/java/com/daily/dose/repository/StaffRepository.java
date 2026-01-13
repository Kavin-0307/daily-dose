package com.daily.dose.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daily.dose.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
	Optional<Staff> findByStaffEmail(String email);
	long countByStaffRoleAndStaffActive(String staffRole, boolean staffActive);

}
