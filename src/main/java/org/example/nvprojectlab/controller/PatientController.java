package org.example.nvprojectlab.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.nvprojectlab.dto.PatientCreateRequest;
import org.example.nvprojectlab.dto.PatientResponse;
import org.example.nvprojectlab.dto.QueuePositionResponse;
import org.example.nvprojectlab.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    // 1. Add patient to queue
    @PostMapping
    public ResponseEntity<PatientResponse> addPatient(
            @Valid @RequestBody PatientCreateRequest request) {

        PatientResponse response = patientService.addPatient(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. Get all waiting patients
    @GetMapping
    public ResponseEntity<List<PatientResponse>> getWaitingPatients() {

        return ResponseEntity.ok(
                patientService.getWaitingPatients()
        );
    }

    // 3. Get patient's queue position
    @GetMapping("/{id}")
    public ResponseEntity<QueuePositionResponse> getQueuePosition(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                patientService.getQueuePosition(id)
        );
    }

    // 4. Call next patient
    @PostMapping("/next")
    public ResponseEntity<PatientResponse> callNextPatient() {

        return ResponseEntity.ok(
                patientService.callNextPatient()
        );
    }

    // 5. Remove patient from queue
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePatient(
            @PathVariable Long id) {

        patientService.removePatient(id);

        return ResponseEntity.noContent().build();
    }
}