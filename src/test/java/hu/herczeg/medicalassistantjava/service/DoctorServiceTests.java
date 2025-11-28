package hu.herczeg.medicalassistantjava.service;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.LoginDoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.RegisterDoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.UpdateDoctorDto;
import hu.herczeg.medicalassistantjava.model.Doctor;
import hu.herczeg.medicalassistantjava.model.Medication;
import hu.herczeg.medicalassistantjava.model.Patient;
import hu.herczeg.medicalassistantjava.repository.DoctorRepository;
import hu.herczeg.medicalassistantjava.repository.PatientRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.DoctorService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DoctorServiceTests {

    @MockitoBean
    private DoctorRepository doctorRepository;

    @MockitoBean
    private PatientRepository patientRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DoctorService doctorService;

    Doctor doctor = new Doctor(1L, "testtest", "test", "123", "test@gmail.com", "xyzzzzz");

    private RegisterDoctorDto createRegisterDto() {
        return new RegisterDoctorDto("John Doe", "test str","123","john.doe@test.com", "password123!");
    }

    private Doctor createDoctor() {
        return new Doctor(1L,"John Doe", "test str","123","john.doe@test.com", "password1234!");
    }

    @BeforeEach
    public void setup() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any())).thenReturn(doctor);
    }

    @Test
    public void getAllDoctors_WhenNotEmpty_ReturnAll() {
        assertNotNull(doctorService.getAllDoctors());
    }

    @Test
    public void validId_GetById_ReturnDoctor() {
        assertNotNull(doctorService.getDoctorById(1L));
    }

    @Test
    public void invalidId_GetById_Fail() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> doctorService.getDoctorById(1L));
    }

    @Test
    public void validName_GetByName_ReturnDoctors() {
        when(doctorRepository.findAllByNameContainingIgnoreCase(any(String.class))).thenReturn(List.of(doctor));
        assertNotNull(doctorService.getDoctorByName("test"));
    }

    @Test
    public void validData_createDoctor_ReturnDoctor() {
        RegisterDoctorDto dto = createRegisterDto();
        Doctor expected = createDoctor();

        when(doctorRepository.save(any())).thenReturn(expected);

        when(doctorRepository.existsDoctorByEmail(any())).thenReturn(false);

        when(passwordEncoder.encode(any())).thenReturn("password1234!");

        var result = doctorService.createDoctor(dto);

        assertNotNull(result);
        assertEquals(expected.getEmail(), result.getEmail());
        verify(doctorRepository, times(1)).save(any());
    }

    @Test
    public void emailExists_createDoctor_Fail() {
        RegisterDoctorDto dto = createRegisterDto();

        when(doctorRepository.existsDoctorByEmail(any())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> doctorService.createDoctor(dto));
    }

    @Test
    public void validId_UpdateDoctor_Success(){
        UpdateDoctorDto dto = new UpdateDoctorDto("newName", "address", "123", "email@gmail.com");
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        var result = doctorService.updateDoctor(1L,dto);

        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getAddress(), result.getAddress());
        verify(doctorRepository, times(1)).save(any());
    }

    @Test
    public void invalidId_UpdateDoctor_Fail() {
        UpdateDoctorDto dto = new UpdateDoctorDto();
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> doctorService.updateDoctor(1L,dto));
    }

    @Test
    public void pwdMatch_UpdatePwd_Success() {
        PasswordUpdateDto dto = new PasswordUpdateDto("password1234!","NewPWD12345");
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        assertTrue(doctorService.updateDoctorPassword(1L, dto));
    }

    @Test
    public void pwdNoMatch_UpdatePwd_Fail() {
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> doctorService.updateDoctorPassword(1L, new PasswordUpdateDto()));
    }

    @Test
    public void invalidId_UpdatePwd_Fail() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> doctorService.updateDoctorPassword(1L,new PasswordUpdateDto()));
    }

    @Test
    public void validId_DeleteDoctor_Success() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).deleteById(anyLong());

        assertTrue(doctorService.deleteDoctor(1L));
    }

    @Test
    public void invalidId_DeleteDoctor_Fail() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> doctorService.deleteDoctor(1L));
    }

    @Test
    public void validId_GetPatients_Success() {
        when(patientRepository.findAllByDoctor(any(Doctor.class))).thenReturn(List.of(new Patient()));

        assertNotNull(doctorService.getPatientsOfDoctor(1L));
    }

    @Test
    public void invalidId_GetPatients_Fail() {
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> doctorService.getPatientsOfDoctor(1L));
    }

    @Test
    public void validIds_AddPatient_Success() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(new Doctor()));

        doctorService.addPatient(1L,2L);
        verify(patientRepository, times(1)).save(any());
    }

    @Test
    public void invalidIds_AddPatient_Fail() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> doctorService.addPatient(1L,2L));
        verify(patientRepository, times(0)).save(any());
    }

    @Test
    public void validIds_RemovePatient_Success() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(new Doctor()));

        doctorService.removePatient(1L,2L);
        verify(patientRepository, times(1)).save(any());
    }

    @Test
    public void invalidIds_RemovePatient_Fail() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(new Doctor()));

        assertThrows(NoSuchElementException.class, () -> doctorService.removePatient(1L,2L));
    }

    @Test
    public void validCreditentials_LoginDoctor_Success() {
        when(doctorRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(doctor));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        var result = doctorService.loginDoctor(new LoginDoctorDto("email@gmail.com", "password"));

        assertNotNull(result);
        assertEquals(doctor.getName(), result.getDoctor().name);
    }

    @Test
    public void invalidPassword_LoginDoctor_Fail() {
        when(doctorRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(doctor));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> doctorService.loginDoctor(new LoginDoctorDto("email@gmail.com", "password")));
    }
    @Test
    public void invalidEmail_LoginDoctor_Fail() {
        when(doctorRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        assertThrows(NoSuchElementException.class,
                () -> doctorService.loginDoctor(new LoginDoctorDto("email@gmail.com", "password")));
    }

    @Test
    public void addPatientMedication_Success() {
        Patient patient = Patient.builder()
                .medications(new ArrayList<Medication>()).build();
        when(patientRepository.findByTaj(anyString())).thenReturn(patient);
        assertDoesNotThrow(() -> doctorService.addPatientMedication("111-111-111", "title", "description"));
    }

    @Test
    public void addPatientMedication_Fail() {
        when(patientRepository.findByTaj(anyString())).thenReturn(null);
        assertThrows(IllegalArgumentException.class,() -> doctorService.addPatientMedication("111-111-111", "title", "description"));
    }

    @Test
    public void nomedication_RemovePatientMedication_Fail() {
        UUID medication = UUID.randomUUID();
        Patient patient = Patient.builder()
                .medications(new ArrayList<Medication>()).build();
        when(patientRepository.findByTaj(anyString())).thenReturn(patient);

        assertThrows(IllegalArgumentException.class,() -> doctorService.removePatientMedication("111-111-111", medication));

    }

    @Test
    public void removePatientMedication_Fail() {
        when(patientRepository.findByTaj(anyString())).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> doctorService.removePatientMedication("111-111-111", UUID.randomUUID()));
    }

}