package org.example.nvprojectlab.dto;

public record QueuePositionResponse(
        Long patientId,
        String name,
        int position
) {
}