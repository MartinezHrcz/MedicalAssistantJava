package hu.herczeg.medicalassistantjava.service.impl;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.*;
import hu.herczeg.medicalassistantjava.mappers.PatientMapper;
import hu.herczeg.medicalassistantjava.model.Patient;
import hu.herczeg.medicalassistantjava.repository.PatientRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.PatientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PasswordEncoder passwordEncoder, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<PatientDto> GetAllPatients() {
        return patientMapper.toDtos(patientRepository.findAll());
    }

    @Override
    public PatientDto GetPatientById(Long id) {
        return patientMapper.toDto(patientRepository.findById(id).orElseThrow(
                NoSuchElementException::new
        ));
    }

    @Override
    public List<PatientDto> GetPatientByName(String name) {
        List<Patient> patient;
        patient = patientRepository.findAllByNameContainingIgnoreCase(name);
        return patientMapper.toDtos(patient);
    }

    @Override
    public PatientDto GetPatientByTaj(String taj) {
        return patientMapper.toDto(patientRepository.findByTaj(taj));
    }

    @Override
    public PatientDto CreatePatient(RegisterPatientDto dto) {
        Patient patient = patientMapper.toEntity(dto);
        patient.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDto UpdatePatient(Long id, UpdatePatientDto dto) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                NoSuchElementException::new
        );
        patient.setName(dto.name);
        patient.setTaj(dto.taj);
        patient.setAddress(dto.address);
        patient.setComplaints(dto.complaints);
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public boolean UpdatePatientPassword(Long id, PasswordUpdateDto dto) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                NoSuchElementException::new
        );
        if (!passwordEncoder.matches(dto.oldPassword, patient.getPasswordHash())){
            throw new IllegalArgumentException("Old password does not match hash");
        }
        patient.setPasswordHash(passwordEncoder.encode(dto.newPassword));
        return true;
    }

    @Override
    public void DeletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public PatientAuthResponseDto LoginPatientAsync(PatientLoginDto dto) {
        Patient patient = patientRepository.findByTaj(dto.taj);
        if(!passwordEncoder.matches(dto.password, patient.getPasswordHash()))
        {
            throw new IllegalArgumentException("Old password does not match hash");
        }
        return new PatientAuthResponseDto(patientMapper.toDto(patient), "");
    }

    @Override
    public PatientMedicationDto GetPatientMedication(String taj) {
        Patient patient = patientRepository.findByTaj(taj);
        return patientMapper.toMedicationDto(patient);
    }
}
