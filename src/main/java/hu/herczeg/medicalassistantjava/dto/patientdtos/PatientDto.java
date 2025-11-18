package hu.herczeg.medicalassistantjava.dto.patientdtos;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    public Long Id;
    public String Name;
    public String Address;
    public String Taj;
    public String Complaints;
    public LocalDateTime TimeOfAdmission;
}

