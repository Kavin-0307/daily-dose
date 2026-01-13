package com.daily.dose.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daily.dose.entity.Receipts;

import jakarta.persistence.LockModeType;

public interface ReceiptRepository extends JpaRepository<Receipts,Long> {
	boolean existsByReceiptNumber(String receiptNumber);
	List<Receipts> findAllByPatient_PatientId(Long patientId);
	Optional<Receipts> findByVisit_VisitId(Long visitId);
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select count(r) from Receipts r where r.receiptNumber like :prefix%")
	long countForYearWithLock(@Param("prefix") String prefix);


}
