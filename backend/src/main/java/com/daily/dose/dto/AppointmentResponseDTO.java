package com.daily.dose.dto;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(
    Long appointmentId,
    Long patientId,
    String patientName,
    Long staffId,
    String staffName,
    LocalDateTime appointmentTime,
    String reason,
    String status
) {}
