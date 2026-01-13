package com.daily.dose.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daily.dose.entity.PatientData;

import jakarta.persistence.LockModeType;

public interface PatientRepository extends JpaRepository<PatientData,Long>{
	
	List<PatientData> findByPatientName(String patientName);
	@Query("""
			SELECT COUNT(p) FROM PatientData p
			WHERE p.patientNumber LIKE CONCAT(:prefix, '%')
			""")
			@Lock(LockModeType.PESSIMISTIC_WRITE)
			long countForYearWithLock(@Param("prefix") String prefix);
	@Query("""
		    SELECT DISTINCT p
		    FROM PatientData p
		    JOIN VisitData v ON v.patient.patientId = p.patientId
		    WHERE v.staff.staffId = :staffId
		      AND p.patientStatus = true
		""")
		List<PatientData> findPatientsByStaffId(Long staffId);


	
}
