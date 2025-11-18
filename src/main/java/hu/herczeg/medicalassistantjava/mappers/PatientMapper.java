package hu.herczeg.medicalassistantjava.mappers;

import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientMedicationDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.RegisterPatientDto;
import hu.herczeg.medicalassistantjava.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto patientDto);

    Patient toEntity(RegisterPatientDto dto);

    PatientMedicationDto toMedicationDto(Patient patient);

    List<PatientDto> toDtos(List<Patient> patients);
}
