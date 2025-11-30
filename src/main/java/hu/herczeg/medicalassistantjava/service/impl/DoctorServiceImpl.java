package hu.herczeg.medicalassistantjava.service.impl;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.RegisterDoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.DoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.UpdateDoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.DoctorAuthResponseDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.LoginDoctorDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.mappers.DoctorMapper;
import hu.herczeg.medicalassistantjava.mappers.PatientMapper;
import hu.herczeg.medicalassistantjava.model.Doctor;
import hu.herczeg.medicalassistantjava.model.Medication;
import hu.herczeg.medicalassistantjava.model.Patient;
import hu.herczeg.medicalassistantjava.repository.DoctorRepository;
import hu.herczeg.medicalassistantjava.repository.PatientRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             PatientRepository patientRepository,
                             PasswordEncoder passwordEncoder,
                             DoctorMapper doctorMapper,
                             PatientMapper patientMapper) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.doctorMapper = doctorMapper;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorMapper.toDtos(doctorRepository.findAll());
    }

    @Override
    public DoctorDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return doctorMapper.toDto(doctor);
    }

    @Override
    public List<DoctorDto> getDoctorByName(String name) {
        return doctorMapper.toDtos(doctorRepository.findAllByNameContainingIgnoreCase(name));
    }

    @Override
    public DoctorDto createDoctor(RegisterDoctorDto dto) {
        if (doctorRepository.existsDoctorByEmail(dto.email)) {
            throw new IllegalArgumentException("Doctor already exists");
        }
        Doctor doctor =  doctorMapper.toEntity(dto);
        doctor.setPasswordHash(passwordEncoder.encode(dto.password));
        doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    @Override
    public DoctorDto updateDoctor(Long id, UpdateDoctorDto dto) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        doctor.setName(dto.name);
        doctor.setEmail(dto.email);
        doctor.setAddress(dto.address);
        doctor.setPhone(dto.phone);
        doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    @Override
    public boolean updateDoctorPassword(Long id, PasswordUpdateDto dto) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (!passwordEncoder.matches(dto.oldPassword,doctor.getPasswordHash())) {
            throw new IllegalArgumentException("Old Password does not match");
        }
        doctor.setPasswordHash(passwordEncoder.encode(dto.newPassword));
        doctorRepository.save(doctor);
        return true;
    }

    @Override
    public boolean deleteDoctor(Long id) {
        if (doctorRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Doctor does not exist");
        }
        patientRepository.findAll().forEach(patient -> {
            patient.setDoctor(null);
            patientRepository.save(patient);
        });
        doctorRepository.deleteById(id);
        return true;
    }

    @Override
    public List<PatientDto> getPatientsOfDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        List<Patient> patients = patientRepository.findAllByDoctor(doctor);
        return patientMapper.toDtos(patients);
    }

    @Override
    public void addPatient(Long doctorId, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(NoSuchElementException::new);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(NoSuchElementException::new);
        patient.setDoctor(doctor);
        patientRepository.save(patient);
    }

    @Override
    public void removePatient(Long doctorId, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(NoSuchElementException::new);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(NoSuchElementException::new);
        if (patient.getDoctor() != null) {
            if (!patient.getDoctor().equals(doctor)) {
                throw new IllegalArgumentException("Patient does not belong to doctor");
            }
        }
        patient.setDoctor(null);
        patientRepository.save(patient);
    }

    @Override
    public DoctorAuthResponseDto loginDoctor(LoginDoctorDto loginDoctorDto) {
        Doctor doctor;
        doctor = doctorRepository.findByEmailEqualsIgnoreCase(loginDoctorDto.email).orElseThrow(
                () -> new NoSuchElementException("Doctor not found"));
        if (!passwordEncoder.matches(loginDoctorDto.password, doctor.getPasswordHash())) {
            throw new IllegalArgumentException("Wrong Password");
        }

        return new DoctorAuthResponseDto(doctorMapper.toDto(doctor), "");
    }

    @Override
    public void addPatientMedication(String taj, String title, String medication) {
        Patient patient = patientRepository.findByTaj(taj);
        if (patient == null) {
            throw new IllegalArgumentException("Patient does not exist");
        }
        patient.getMedications()
                .add(new Medication(UUID.randomUUID(),title, medication, patient));
        patientRepository.save(patient);
    }

    @Override
    public void removePatientMedication(String taj, UUID medication) {
        Patient patient = patientRepository.findByTaj(taj);
        if (patient == null) {
            throw new IllegalArgumentException("Patient does not exist");
        }
        if (patient.getMedications()
                .stream().noneMatch(m -> m.getId().equals(medication))) {
            throw new IllegalArgumentException("Medication does not exist");
        }
        patient.getMedications().removeIf(m -> m.getId().equals(medication));
        patientRepository.save(patient);
    }
}
