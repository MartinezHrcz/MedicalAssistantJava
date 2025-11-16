package hu.herczeg.medicalassistantjava.service.interfaces;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.*;
import hu.herczeg.medicalassistantjava.model.Patient;

import java.util.List;

public interface PatientService {
    List<PatientDto> GetAllPatients();
    PatientDto GetPatientById(Long id);
    List<PatientDto> GetPatientByName(String name);
    PatientDto GetPatientByTaj(String taj);
    PatientDto CreatePatient(RegisterPatientDto dto);
    PatientDto UpdatePatient(Long id, UpdatePatientDto dto);
    boolean UpdatePatientPassword(Long id, PasswordUpdateDto dto);
    void DeletePatient(int id);
    PatientAuthResponseDto LoginPatientAsync(PatientLoginDto dto);
    PatientMedicationDto GetPatientMedication(String taj);

}
