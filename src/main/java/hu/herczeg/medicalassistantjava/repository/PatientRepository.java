package hu.herczeg.medicalassistantjava.repository;

import hu.herczeg.medicalassistantjava.model.Doctor;
import hu.herczeg.medicalassistantjava.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByDoctor(Doctor doctor);

    Patient findByTaj(String taj);
}
