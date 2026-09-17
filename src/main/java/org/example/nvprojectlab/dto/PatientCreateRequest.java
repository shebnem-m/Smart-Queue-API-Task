package org.example.nvprojectlab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PatientCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotNull(message = "Date of birth is required")
        LocalDate dateOfBirth
) {
}