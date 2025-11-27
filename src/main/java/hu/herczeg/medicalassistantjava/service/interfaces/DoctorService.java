package hu.herczeg.medicalassistantjava.service.interfaces;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.*;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    List<DoctorDto> getAllDoctors();

    DoctorDto getDoctorById(Long id);

    List<DoctorDto> getDoctorByName(String name);

    DoctorDto createDoctor(RegisterDoctorDto dto);

    DoctorDto updateDoctor(Long id, UpdateDoctorDto dto);

    boolean updateDoctorPassword(Long id, PasswordUpdateDto dto);

    boolean deleteDoctor(Long id);

    List<PatientDto> getPatientsOfDoctor(Long id);

    void addPatient(Long doctorId, Long patientId);

    void removePatient(Long doctorId, Long patientId);

    DoctorAuthResponseDto loginDoctor(LoginDoctorDto loginDoctorDto);

    void addPatientMedication(String taj, String title, String medication);

    void removePatientMedication(String taj, UUID medication);
}
