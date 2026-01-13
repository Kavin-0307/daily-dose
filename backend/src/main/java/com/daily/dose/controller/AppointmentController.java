package com.daily.dose.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.daily.dose.dto.AppointmentRequestDTO;
import com.daily.dose.dto.AppointmentResponseDTO;
import com.daily.dose.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDTO> create(
            @Valid @RequestBody AppointmentRequestDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointmentService.create(dto));
    }

    @GetMapping("/staff/{staffId}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getForStaff(
            @PathVariable Long staffId
    ) {
        return ResponseEntity.ok(
                appointmentService.getByStaff(staffId)
        );
    }
    @GetMapping("/patients/{patientId}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getForPatient(
            @PathVariable Long patientId
    ) {
        return ResponseEntity.ok(
                appointmentService.getByPatient(patientId)
        );
    }

    @PutMapping("/appointments/{id}/cancel")
    public ResponseEntity<Void> cancel(
            @PathVariable Long id
    ) {
        appointmentService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
