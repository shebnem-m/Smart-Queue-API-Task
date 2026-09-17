package org.example.nvprojectlab.dto;

import org.example.nvprojectlab.enums.QueueStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientResponse(
        Long id,
        String name,
        String phoneNumber,
        LocalDate dateOfBirth,
        LocalDateTime createdAt,
        QueueStatus status
) {
}