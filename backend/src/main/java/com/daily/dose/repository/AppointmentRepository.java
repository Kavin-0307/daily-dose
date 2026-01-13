package com.daily.dose.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daily.dose.entity.Appointment;

@Repository
public interface AppointmentRepository 
    extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStaff_StaffId(Long staffId);

    List<Appointment> findByPatient_PatientId(Long patientId);

    List<Appointment> findByStatus(String status);
}
