package hu.herczeg.medicalassistantjava.dto.patientdtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientAuthResponseDto{
    PatientDto patient;
    public String token;
}
