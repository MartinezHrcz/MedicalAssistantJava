package hu.herczeg.medicalassistantjava.service.impl;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.*;
import hu.herczeg.medicalassistantjava.service.interfaces.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {
    @Override
    public List<PatientDto> GetAllPatients() {
        return List.of();
    }

    @Override
    public PatientDto GetPatientById(Long id) {
        return null;
    }

    @Override
    public List<PatientDto> GetPatientByName(String name) {
        return List.of();
    }

    @Override
    public PatientDto GetPatientByTaj(String taj) {
        return null;
    }

    @Override
    public PatientDto CreatePatient(RegisterPatientDto dto) {
        return null;
    }

    @Override
    public PatientDto UpdatePatient(Long id, UpdatePatientDto dto) {
        return null;
    }

    @Override
    public boolean UpdatePatientPassword(Long id, PasswordUpdateDto dto) {
        return false;
    }

    @Override
    public void DeletePatient(int id) {

    }

    @Override
    public PatientAuthResponseDto LoginPatientAsync(PatientLoginDto dto) {
        return null;
    }

    @Override
    public PatientMedicationDto GetPatientMedication(String taj) {
        return null;
    }
}
