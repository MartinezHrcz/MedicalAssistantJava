package hu.herczeg.medicalassistantjava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.lang.model.util.Elements;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[A-Za-zÀ-ž\\ \\s'-]{2,50}$")
    @Column(nullable = false, length = 100)
    private String name;

    private String address;

    @Column(nullable = false, length = 12)
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$")
    private String taj;

    @Column(nullable = true, length = 200)
    private String complaints;

    private LocalDateTime timeOfAdmission;

    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Medication> medications;
}
