package hu.herczeg.medicalassistantjava.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "DOCTORS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = true, length = 100)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String email;

    private String PasswordHash;

}
