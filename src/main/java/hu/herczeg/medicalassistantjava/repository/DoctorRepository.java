package hu.herczeg.medicalassistantjava.repository;

import hu.herczeg.medicalassistantjava.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
