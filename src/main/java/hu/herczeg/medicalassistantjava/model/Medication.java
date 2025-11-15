package hu.herczeg.medicalassistantjava.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.id.GUIDGenerator;

import java.util.UUID;

@Entity
@Table(name = "MEDS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
    @Id
    private UUID id;

    private String title;

    private String description;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
