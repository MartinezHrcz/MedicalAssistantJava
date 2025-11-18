package hu.herczeg.medicalassistantjava.dto.patientdtos;

import hu.herczeg.medicalassistantjava.model.Medication;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PatientMedicationDto{
    public Long id;
    public String taj;
    private List<Medication> medications;
}

