package hu.herczeg.medicalassistantjava.repository;

import hu.herczeg.medicalassistantjava.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, UUID> {
}
