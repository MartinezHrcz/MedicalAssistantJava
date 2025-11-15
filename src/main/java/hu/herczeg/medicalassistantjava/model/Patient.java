package hu.herczeg.medicalassistantjava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.util.Elements;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue
    private UUID id;
    @Pattern(regexp = "^[A-Za-zÀ-ž\\ \\s'-]{2,50}$")
    @Column(nullable = false, length = 100)
    private String name;

    private String address;

    @Column(nullable = false, length = 12)
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$")
    private String taj;

    @Column(nullable = true, length = 200)
    private String complaints;

    private LocalDateTime timeOfAddmission;

    private String passwordhash;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public List<Medication> medications;
}
