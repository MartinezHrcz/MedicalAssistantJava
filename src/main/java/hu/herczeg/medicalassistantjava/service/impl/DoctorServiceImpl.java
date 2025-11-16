package hu.herczeg.medicalassistantjava.service.impl;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.*;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.repository.DoctorRepository;
import hu.herczeg.medicalassistantjava.repository.PatientRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.DoctorService;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<DoctorDto> GetAllDoctors() {
        return List.of();
    }

    @Override
    public DoctorDto GetDoctorById(Long id) {
        return null;
    }

    @Override
    public List<DoctorDto> GetDoctorByName(String name) {
        return List.of();
    }

    @Override
    public DoctorDto CreateDoctor(RegisterDoctorDto dto) {
        return null;
    }

    @Override
    public DoctorDto UpdateDoctor(Long id, UpdateDoctorDto dto) {
        return null;
    }

    @Override
    public boolean UpdateDoctorPassword(Long id, PasswordUpdateDto dto) {
        return false;
    }

    @Override
    public boolean DeleteDoctor(Long id) {
        return false;
    }

    @Override
    public List<PatientDto> GetPatientsOfDoctor(Long id) {
        return List.of();
    }

    @Override
    public void AddPatient(Long doctorId, Long patientId) {

    }

    @Override
    public void RemovePatient(Long doctorId, Long patientId) {

    }

    @Override
    public DoctorAuthResponseDto LoginDoctor(LoginDoctorDto loginDoctorDto) {
        return null;
    }

    @Override
    public void AddPatientMedication(String taj, String title, String medication) {

    }

    @Override
    public void RemovePatientMedication(String taj, UUID medication) {

    }
}
