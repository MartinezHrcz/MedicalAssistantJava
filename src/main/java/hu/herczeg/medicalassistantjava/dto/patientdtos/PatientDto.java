package hu.herczeg.medicalassistantjava.dto.patientdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PatientDto {
    public Long Id;
    public String Name;
    public String Address;
    public String Taj;
    public String Complaints;
    public LocalDateTime TimeOfAdmission;
}

