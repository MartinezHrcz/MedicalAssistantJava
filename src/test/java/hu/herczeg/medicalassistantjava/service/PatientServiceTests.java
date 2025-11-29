package hu.herczeg.medicalassistantjava.service;


import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.UpdateDoctorDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientLoginDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.RegisterPatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.UpdatePatientDto;
import hu.herczeg.medicalassistantjava.model.Doctor;
import hu.herczeg.medicalassistantjava.model.Medication;
import hu.herczeg.medicalassistantjava.model.Patient;
import hu.herczeg.medicalassistantjava.repository.PatientRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.PatientService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientServiceTests {

    @Autowired
    private PatientService patientService;

    @MockitoBean
    private PatientRepository patientRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    static Patient patient =
            new Patient(1L,
                    "testPatient",
                    "address",
                    "111-111-111",
                    "Complaints",
                    LocalDateTime.now(),
                    "PasswordHash",
                    new Doctor(),
                    new ArrayList<Medication>()
            );

    @BeforeEach
    public void setup() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.findAll()).thenReturn(List.of(patient));
        when(passwordEncoder.encode(anyString())).thenReturn("PasswordHash");
        when(patientRepository.save(any())).thenReturn(patient);
    }

    @Test
    public void getAllPatients_WhenNotEmpty_ReturnAll() {
        assertNotNull(patientService.getAllPatients());
    }

    @Test
    public void validId_GetById_ReturnPatient() {
        assertNotNull(patientService.getPatientById(1L));
    }

    @Test
    public void invalidId_GetById_Fail() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> patientService.getPatientById(1L));
    }

    @Test
    public void validData_createPatient_ReturnPatient() {
        RegisterPatientDto dto = RegisterPatientDto.builder()
                .name("testPatient")
                .taj("111-111-111")
                .complaints("Complaints")
                .password("New password")
                .build();
        var result = patientService.createPatient(dto);

        assertNotNull(result);
        assertEquals(patient.getTaj(), dto.getTaj());
    }

    @Test
    public void validId_UpdatePatient_Success(){
        UpdatePatientDto dto = UpdatePatientDto.builder()
                .name("testPatient")
                .taj("111-111-111")
                .complaints("Complaints")
                .address("new address").build();
        long updateId = 1L;

        var result = patientService.updatePatient(updateId, dto);

        assertNotNull(result);
        assertEquals(dto.getTaj(), result.getTaj());
        assertEquals(dto.getAddress(), result.getAddress());
    }

    @Test
    public void invalidId_UpdatePatient_Fail() {
        UpdatePatientDto dto = UpdatePatientDto.builder()
                .name("testPatient")
                .taj("111-111-111")
                .complaints("Complaints")
                .address("new address").build();
        long updateId = 1L;
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> patientService.updatePatient(updateId, dto));
    }

    @Test
    public void pwdMatch_UpdatePwd_Success() {
        PasswordUpdateDto dto = new PasswordUpdateDto("Old password", "New password");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        assertTrue(patientService.updatePatientPassword(1L, dto));
    }

    @Test
    public void pwdNoMatch_UpdatePwd_Fail() {
        PasswordUpdateDto dto = new PasswordUpdateDto("Invalid password", "New password");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> patientService.updatePatientPassword(1L, dto));
    }

    @Test
    public void invalidId_UpdatePwd_Fail() {
        PasswordUpdateDto dto = new PasswordUpdateDto("Invalid password", "New password");
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> patientService.updatePatientPassword(1L, dto));
    }

    @Test
    public void validId_DeletePatient_Success() {
        assertDoesNotThrow(() -> patientService.deletePatient(1L));
    }

    @Test
    public void invalidId_DeletePatient_Fail() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> patientService.deletePatient(1L));
    }

    @Test
    public void invalidPassword_LoginPatient_Fail() {
        when(patientRepository.findByTaj(anyString())).thenReturn(patient);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> patientService.loginPatientAsync(new PatientLoginDto("111-111-111", "pwd")));
    }

    @Test
    public void invalidTaj_LoginPatient_Fail() {
        when(patientRepository.findByTaj(anyString())).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> patientService.loginPatientAsync(new PatientLoginDto()));
    }
}
