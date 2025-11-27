package hu.herczeg.medicalassistantjava.dto.patientdtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientAuthResponseDto {
    PatientDto patient;
    public String token;
}
