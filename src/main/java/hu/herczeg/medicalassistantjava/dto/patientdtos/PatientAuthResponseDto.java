package hu.herczeg.medicalassistantjava.dto.patientdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PatientAuthResponseDto{
    PatientDto patientDto;
    public String token;
}
