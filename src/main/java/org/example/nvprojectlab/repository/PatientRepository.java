package org.example.nvprojectlab.repository;

import jakarta.persistence.LockModeType;
import org.example.nvprojectlab.entity.Patient;
import org.example.nvprojectlab.enums.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findByStatusOrderByCreatedAtAsc(QueueStatus status);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Patient p WHERE p.status = :status ORDER BY p.createdAt ASC")
    List<Patient> findFirstWaitingPatientForUpdate(QueueStatus status);
}
