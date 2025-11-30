package hu.herczeg.medicalassistantjava.service.impl;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientMedicationDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientLoginDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.RegisterPatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientAuthResponseDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.UpdatePatientDto;
import hu.herczeg.medicalassistantjava.mappers.PatientMapper;
import hu.herczeg.medicalassistantjava.model.Patient;
import hu.herczeg.medicalassistantjava.repository.PatientRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.PatientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository,
                              PasswordEncoder passwordEncoder,
                              PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientMapper.toDtos(patientRepository.findAll());
    }

    @Override
    public PatientDto getPatientById(Long id) {
        return patientMapper.toDto(patientRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<PatientDto> getPatientByName(String name) {
        List<Patient> patient;
        patient = patientRepository.findAllByNameContainingIgnoreCase(name);
        return patientMapper.toDtos(patient);
    }

    @Override
    public PatientDto getPatientByTaj(String taj) {
        return patientMapper.toDto(patientRepository.findByTaj(taj));
    }

    @Override
    public PatientDto createPatient(RegisterPatientDto dto) {
        Patient patient = patientMapper.toEntity(dto);
        patient.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        patient.setTimeOfAdmission(LocalDateTime.now());
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDto updatePatient(Long id, UpdatePatientDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        patient.setName(dto.name);
        patient.setTaj(dto.taj);
        patient.setAddress(dto.address);
        patient.setComplaints(dto.complaints);
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public boolean updatePatientPassword(Long id, PasswordUpdateDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        if (!passwordEncoder.matches(dto.oldPassword, patient.getPasswordHash())) {
            throw new IllegalArgumentException("Old password does not match hash");
        }
        patient.setPasswordHash(passwordEncoder.encode(dto.newPassword));
        patientRepository.save(patient);
        return true;
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(NoSuchElementException::new);
        patientRepository.delete(patient);
    }

    @Override
    public PatientAuthResponseDto loginPatientAsync(PatientLoginDto dto) {
        Patient patient = patientRepository.findByTaj(dto.taj);

        if (patient == null) {
            throw new NoSuchElementException("Patient not found");
        }

        if (!passwordEncoder.matches(dto.password, patient.getPasswordHash())) {
            throw new IllegalArgumentException("Old password does not match hash");
        }
        return new PatientAuthResponseDto(patientMapper.toDto(patient), "");
    }

    @Override
    public PatientMedicationDto getPatientMedication(String taj) {
        Patient patient = patientRepository.findByTaj(taj);
        return patientMapper.toMedicationDto(patient);
    }
}
