package hu.herczeg.medicalassistantjava.service.interfaces;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.*;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    List<DoctorDto> GetAllDoctors();
    DoctorDto GetDoctorById(Long id);
    List<DoctorDto> GetDoctorByName(String name);
    DoctorDto CreateDoctor(RegisterDoctorDto dto);
    DoctorDto UpdateDoctor(Long id, UpdateDoctorDto dto);
    boolean UpdateDoctorPassword(Long id, PasswordUpdateDto dto);
    boolean DeleteDoctor(Long id);
    List<PatientDto> GetPatientsOfDoctor(Long id);
    void AddPatient(Long doctorId, Long patientId);
    void RemovePatient(Long doctorId, Long patientId);
    DoctorAuthResponseDto LoginDoctor(LoginDoctorDto loginDoctorDto);
    void AddPatientMedication(String taj, String title, String medication);
    void RemovePatientMedication(String taj, UUID medication);
}
