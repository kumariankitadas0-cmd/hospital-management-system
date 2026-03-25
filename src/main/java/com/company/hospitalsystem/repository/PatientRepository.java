package com.company.hospitalsystem.repository;

import com.company.hospitalsystem.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Notice we changed List<Patient> to Page<Patient>, and added Pageable
    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(p.diagnosis) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Patient> search(String keyword, Pageable pageable);
}