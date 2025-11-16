package hu.herczeg.medicalassistantjava.repository;

import hu.herczeg.medicalassistantjava.model.Doctor;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAllByNameContainingIgnoreCase(String name);

    boolean findByEmailEquals(String email);

    Doctor getDoctorByEmail(@Email String email);

    Doctor findByEmailEqualsIgnoreCase(String email);
}
