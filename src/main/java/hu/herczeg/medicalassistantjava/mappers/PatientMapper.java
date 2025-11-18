package hu.herczeg.medicalassistantjava.mappers;

import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientMedicationDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.RegisterPatientDto;
import hu.herczeg.medicalassistantjava.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "taj", target = "taj")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "timeOfAdmission", target = "timeOfAdmission")
    @Mapping(source = "complaints", target = "complaints")
    @Mapping(source = "doctor.id", target = "doctor_id")
    PatientDto toDto(Patient patient);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "taj", target = "taj")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "timeOfAdmission", target = "timeOfAdmission")
    @Mapping(source = "complaints", target = "complaints")
    Patient toEntity(PatientDto patientDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "taj", target = "taj")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "timeOfAdmission", target = "timeOfAdmission")
    @Mapping(source = "complaints", target = "complaints")
    Patient toEntity(RegisterPatientDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "taj", target = "taj")
    @Mapping(source = "complaints", target = "complaints")
    PatientMedicationDto toMedicationDto(Patient patient);

    List<PatientDto> toDtos(List<Patient> patients);
}
