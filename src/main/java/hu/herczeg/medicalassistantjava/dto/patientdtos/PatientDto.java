package hu.herczeg.medicalassistantjava.dto.patientdtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    public Long id;
    public String name;
    public String address;
    public String taj;
    public String complaints;
    public Long doctorId = null;
    public LocalDateTime timeOfAdmission;
}

