package hu.herczeg.medicalassistantjava.repository;

import hu.herczeg.medicalassistantjava.model.Doctor;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAllByNameContainingIgnoreCase(String name);

    Optional<Doctor> findByEmailEqualsIgnoreCase(String email);


    boolean existsDoctorByEmail(String email);
}
