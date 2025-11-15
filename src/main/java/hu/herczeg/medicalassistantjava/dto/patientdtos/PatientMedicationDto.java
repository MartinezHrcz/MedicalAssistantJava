package hu.herczeg.medicalassistantjava.dto.patientdtos;

import hu.herczeg.medicalassistantjava.model.Medication;

import java.util.List;

public class PatientMedicationDto{
    public Long Id;
    public String Taj;
    List<Medication> Medications;
}

