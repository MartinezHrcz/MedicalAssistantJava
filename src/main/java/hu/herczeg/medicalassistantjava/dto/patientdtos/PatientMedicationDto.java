package hu.herczeg.medicalassistantjava.dto.patientdtos;

import hu.herczeg.medicalassistantjava.model.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PatientMedicationDto{
    public Long Id;
    public String Taj;
    List<Medication> Medications;
}

