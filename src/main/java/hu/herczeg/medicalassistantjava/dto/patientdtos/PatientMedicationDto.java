package hu.herczeg.medicalassistantjava.dto.patientdtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import hu.herczeg.medicalassistantjava.model.Medication;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PatientMedicationDto {
    public Long id;
    public String taj;
    @JsonManagedReference
    private List<Medication> medications;
}

