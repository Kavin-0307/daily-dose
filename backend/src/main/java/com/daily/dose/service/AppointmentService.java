package com.daily.dose.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.daily.dose.dto.AppointmentRequestDTO;
import com.daily.dose.dto.AppointmentResponseDTO;
import com.daily.dose.entity.Appointment;
import com.daily.dose.entity.PatientData;
import com.daily.dose.entity.Staff;
import com.daily.dose.repository.AppointmentRepository;
import com.daily.dose.repository.PatientRepository;
import com.daily.dose.repository.StaffRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final StaffRepository staffRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            StaffRepository staffRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.staffRepository = staffRepository;
    }
    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {

    	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	    String email = auth.getName();

    	    Staff loggedInStaff = staffRepository.findByStaffEmail(email)
    	        .orElseThrow(() -> new ResponseStatusException(
    	            HttpStatus.FORBIDDEN, "Staff not found"));

    	    PatientData patient = patientRepository.findById(dto.getPatientId())
    	        .orElseThrow(() -> new ResponseStatusException(
    	            HttpStatus.NOT_FOUND, "Patient not found"));

    	    if (!patient.getPatientStatus()) {
    	        throw new ResponseStatusException(
    	            HttpStatus.CONFLICT, "Inactive patient");
    	    }

    	    Staff staff;
    	    if ("ADMIN".equals(loggedInStaff.getStaffRole())) {
    	        staff = staffRepository.findById(dto.getStaffId())
    	            .orElseThrow(() -> new ResponseStatusException(
    	                HttpStatus.NOT_FOUND, "Staff not found"));
    	    } else {
    	        staff = loggedInStaff;
    	    }

    	    if (!staff.isStaffActive()) {
    	        throw new ResponseStatusException(
    	            HttpStatus.CONFLICT, "Inactive staff");
    	    }

    	    Appointment appointment = new Appointment();
    	    appointment.setPatient(patient);
    	    appointment.setStaff(staff);
    	    appointment.setAppointmentTime(dto.getAppointmentTime());
    	    appointment.setReason(dto.getReason());
    	    appointment.setStatus("SCHEDULED");

    	    return toDTO(appointmentRepository.save(appointment));
    	}

    public List<AppointmentResponseDTO> getByStaff(Long staffIdFromPath) {

        Staff effectiveStaff = resolveEffectiveStaff(staffIdFromPath);

        return appointmentRepository
                .findByStaff_StaffId(effectiveStaff.getStaffId())
                .stream()
                .map(this::toDTO)
                .toList();
    }
    public List<AppointmentResponseDTO> getByPatient(Long patientId) {

        PatientData patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient not found"));

        return appointmentRepository
                .findByPatient_PatientId(patient.getPatientId())
                .stream()
                .map(this::toDTO)
                .toList();
    }
    public void cancel(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Appointment not found"));

        if ("COMPLETED".equals(appointment.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Completed appointment cannot be cancelled");
        }

        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);
    }
    private Staff resolveEffectiveStaff(Long staffIdFromPath) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        Staff loggedInStaff = staffRepository.findByStaffEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Staff not found"));

        if ("ADMIN".equals(loggedInStaff.getStaffRole())) {
            return staffRepository.findById(staffIdFromPath)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Staff not found"));
        }

        // STAFF can only see their own appointments
        return loggedInStaff;
    }
    private AppointmentResponseDTO toDTO(Appointment a) {
        return new AppointmentResponseDTO(
                a.getAppointmentId(),
                a.getPatient().getPatientId(),
                a.getPatient().getPatientName(),
                a.getStaff().getStaffId(),
                a.getStaff().getStaffName(),
                a.getAppointmentTime(),
                a.getReason(),
                a.getStatus()
        );
    }
}
