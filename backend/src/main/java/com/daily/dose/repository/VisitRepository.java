package com.daily.dose.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daily.dose.entity.VisitData;
import com.daily.dose.entity.PatientData;

@Repository

public interface VisitRepository extends JpaRepository <VisitData,Long>{
	List<VisitData> findByPatient(PatientData patient);
	List<VisitData> findAllByPatient_PatientId(Long patientId);
	List<VisitData> findByStaff_StaffId(Long staffId);
}
