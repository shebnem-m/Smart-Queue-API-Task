package org.example.nvprojectlab.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.nvprojectlab.dto.PatientCreateRequest;
import org.example.nvprojectlab.dto.PatientResponse;
import org.example.nvprojectlab.dto.QueuePositionResponse;
import org.example.nvprojectlab.entity.Patient;
import org.example.nvprojectlab.enums.QueueStatus;
import org.example.nvprojectlab.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    // 1. Add patient to queue
    public PatientResponse addPatient(PatientCreateRequest request) {

        Patient patient = new Patient();

        patient.setName(request.name());
        patient.setPhoneNumber(request.phoneNumber());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setStatus(QueueStatus.WAITING);

        Patient savedPatient = patientRepository.save(patient);

        return mapToResponse(savedPatient);
    }

    // 2. Get all waiting patients
    public List<PatientResponse> getWaitingPatients() {

        List<Patient> patients =
                patientRepository.findByStatusOrderByCreatedAtAsc(
                        QueueStatus.WAITING
                );

        return patients.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 3. Get patient's position in queue
    public QueuePositionResponse getQueuePosition(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found")
                );

        if (patient.getStatus() != QueueStatus.WAITING) {
            throw new RuntimeException(
                    "Patient is not currently waiting in the queue"
            );
        }

        List<Patient> waitingPatients =
                patientRepository.findByStatusOrderByCreatedAtAsc(
                        QueueStatus.WAITING
                );

        int position = 1;

        for (Patient waitingPatient : waitingPatients) {

            if (waitingPatient.getId().equals(patient.getId())) {
                break;
            }

            position++;
        }

        return new QueuePositionResponse(
                patient.getId(),
                patient.getName(),
                position
        );
    }

    // 4. Call next patient
    @Transactional
    public PatientResponse callNextPatient() {
        List<Patient> waitingPatients = patientRepository
                .findFirstWaitingPatientForUpdate(QueueStatus.WAITING);

        if (waitingPatients.isEmpty()) {
            throw new RuntimeException("No patients waiting in the queue");
        }

        Patient nextPatient = waitingPatients.get(0);
        nextPatient.setStatus(QueueStatus.SERVING);

        return mapToResponse(patientRepository.save(nextPatient));
    }

    // 5. Remove patient from queue
    public void removePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found")
                );

        patientRepository.delete(patient);
    }

    // Mapping method
    private PatientResponse mapToResponse(Patient patient) {

        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getPhoneNumber(),
                patient.getDateOfBirth(),
                patient.getCreatedAt(),
                patient.getStatus()
        );
    }
}