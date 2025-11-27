package hu.herczeg.medicalassistantjava.service.interfaces;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.*;
import hu.herczeg.medicalassistantjava.model.Patient;

import java.util.List;

public interface PatientService {

    List<PatientDto> getAllPatients();

    PatientDto getPatientById(Long id);

    List<PatientDto> getPatientByName(String name);

    PatientDto getPatientByTaj(String taj);

    PatientDto createPatient(RegisterPatientDto dto);

    PatientDto updatePatient(Long id, UpdatePatientDto dto);

    boolean updatePatientPassword(Long id, PasswordUpdateDto dto);

    void deletePatient(Long id);

    PatientAuthResponseDto loginPatientAsync(PatientLoginDto dto);

    PatientMedicationDto getPatientMedication(String taj);

}
