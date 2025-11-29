package hu.herczeg.medicalassistantjava.controller;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.*;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.model.Doctor;
import hu.herczeg.medicalassistantjava.model.Medication;
import hu.herczeg.medicalassistantjava.model.Patient;
import hu.herczeg.medicalassistantjava.service.interfaces.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DoctorControllerTests {

    @Autowired
    private DoctorController doctorController;

    @MockitoBean
    private DoctorService doctorService;

    DoctorDto doctor = new DoctorDto(1L,"John Doe", "test str", "123","jhon.doe@gmail.com");

    private RegisterDoctorDto createRegisterDto() {
        return new RegisterDoctorDto("John Doe", "test str","123","john.doe@test.com", "password123!");
    }

    @BeforeEach
    public void setup() {
        when(doctorService.getAllDoctors()).thenReturn(List.of(doctor));
        when(doctorService.getDoctorById(anyLong())).thenReturn(doctor);
    }

    @Test
    public void getAllDoctors_WhenNotEmpty_ReturnAll() {
        assertNotNull(doctorController.getAllDoctors());
    }

    @Test
    public void validId_GetById_ReturnDoctor() {
        var result = doctorController.getDoctorById(1L);
        assertNotNull(result);
        assertEquals(doctor, result.getBody());
    }

    @Test
    public void invalidId_GetById_Fail() {
        when(doctorService.getDoctorById(anyLong())).thenThrow(NoSuchElementException.class);
        assertEquals(ResponseEntity.badRequest().build(), doctorController.getDoctorById(1L));
    }

    @Test
    public void validName_GetByName_ReturnDoctors() {
        when(doctorService.getDoctorByName(anyString())).thenReturn(List.of(doctor));
        assertNotNull(doctorController.getDoctorByName("test"));
    }

    @Test
    public void validData_createDoctor_ReturnDoctor() {
        RegisterDoctorDto dto = createRegisterDto();
        DoctorDto expected = doctor;

        when(doctorService.createDoctor(any())).thenReturn(expected);

        var result = doctorController.createDoctor(dto);

        assertNotNull(result);
        assertEquals(expected.getEmail(), result.getBody().getEmail());
        verify(doctorService, times(1)).createDoctor(any());
    }

    @Test
    public void emailExists_createDoctor_Fail() {
        RegisterDoctorDto dto = createRegisterDto();

        when(doctorService.createDoctor(any())).thenThrow(IllegalArgumentException.class);

        assertEquals(ResponseEntity.badRequest().build(), doctorController.createDoctor(dto));
    }

    @Test
    public void validId_UpdateDoctor_Success(){
        UpdateDoctorDto dto = new UpdateDoctorDto("newName", "address", "123", "email@gmail.com");

        doctor.setEmail(dto.email);
        doctor.setName(dto.name);
        doctor.setAddress(dto.address);

        when(doctorService.updateDoctor(anyLong(), any(UpdateDoctorDto.class))).thenReturn(doctor);

        var result = doctorController.updateDoctor(1L, dto);

        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getBody().getEmail());
        assertEquals(dto.getName(), result.getBody().getName());
        assertEquals(dto.getAddress(), result.getBody().getAddress());
        verify(doctorService, times(1)).updateDoctor(anyLong(), any(UpdateDoctorDto.class));
    }

    @Test
    public void invalidId_UpdateDoctor_Fail() {
        UpdateDoctorDto dto = new UpdateDoctorDto();
        when(doctorService.updateDoctor(any(),any())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> doctorService.updateDoctor(1L,dto));
    }

    @Test
    public void pwdMatch_UpdatePwd_Success() {
        PasswordUpdateDto dto = new PasswordUpdateDto("password1234!","NewPWD12345");
        when(doctorService.updateDoctorPassword(anyLong(), any(PasswordUpdateDto.class))).thenReturn(true);

        assertEquals(ResponseEntity.ok().build(), doctorController.updateDoctorPwd(1L, dto));
    }

    @Test
    public void pwdNoMatch_UpdatePwd_Fail() {
        when(doctorService.updateDoctorPassword(anyLong(),any(PasswordUpdateDto.class))).thenThrow(IllegalArgumentException.class);

        assertEquals(ResponseEntity.badRequest().build(), doctorController.updateDoctorPwd(1L, new PasswordUpdateDto()));
    }

    @Test
    public void invalidId_UpdatePwd_Fail() {
        when(doctorService.updateDoctorPassword(anyLong(),any(PasswordUpdateDto.class))).thenThrow(NoSuchElementException.class);
        assertEquals(ResponseEntity.badRequest().build(), doctorController.updateDoctorPwd(1L, new PasswordUpdateDto()));
    }

    @Test
    public void validId_DeleteDoctor_Success() {
        when(doctorService.deleteDoctor(anyLong())).thenReturn(true);

        assertEquals(ResponseEntity.noContent().build(),doctorController.deleteDoctor(1L));
    }

    @Test
    public void invalidId_DeleteDoctor_Fail() {
        when(doctorService.deleteDoctor(anyLong())).thenThrow(NoSuchElementException.class);

        assertEquals(ResponseEntity.badRequest().build(),doctorController.deleteDoctor(1L));
    }

    @Test
    public void validId_GetPatients_Success() {
        when(doctorService.getPatientsOfDoctor(anyLong())).thenReturn(List.of(new PatientDto()));

        assertNotNull(doctorController.getPatients(1L));
    }

    @Test
    public void invalidId_GetPatients_Fail() {
        when(doctorService.getPatientsOfDoctor(anyLong())).thenThrow(NoSuchElementException.class);

        assertEquals(ResponseEntity.badRequest().build(),doctorController.getPatients(1L));
    }

    @Test
    public void validIds_AddPatient_Success() {
        doNothing().when(doctorService).addPatient(any(),any());

        assertEquals(ResponseEntity.ok().build().getHeaders(),doctorController.addPatient(1L,1L).getHeaders());
    }

    @Test
    public void invalidIds_AddPatient_Fail() {
        doThrow(NoSuchElementException.class).when(doctorService).addPatient(any(),any());
        assertEquals(ResponseEntity.badRequest().build(),doctorController.addPatient(1L, 1L));
    }

    @Test
    public void validIds_RemovePatient_Success() {
        doNothing().when(doctorService).removePatient(anyLong(),any());

        assertEquals(ResponseEntity.noContent().build(),doctorController.removePatient(1L,1L));
    }

    @Test
    public void validCreditentials_LoginDoctor_Success() {
       when(doctorService.loginDoctor(any(LoginDoctorDto.class))).thenReturn(new DoctorAuthResponseDto(doctor, ""));

       var result = doctorController.loginDoctor(any(LoginDoctorDto.class));

       assertNotNull(result);
       assertEquals(ResponseEntity.ok().build(),result);
    }

    @Test
    public void invalidPassword_LoginDoctor_Fail() {
        when(doctorService.loginDoctor(any(LoginDoctorDto.class))).thenThrow(IllegalArgumentException.class);

        var result = doctorController.loginDoctor(new LoginDoctorDto("email@gmail.com", "invalidpwd"));

        assertEquals(ResponseEntity.badRequest().build(),result);
    }

    @Test
    public void addPatientMedication_Success() {
        doNothing().when(doctorService).addPatient(any(),any());

        assertEquals(ResponseEntity.ok().build(),doctorController.addPatient(1L,1L));
    }

    @Test
    public void addPatientMedication_Fail() {
        doThrow(NoSuchElementException.class).when(doctorService).addPatientMedication(any(),any(),any());

        assertEquals(ResponseEntity.badRequest().build(),doctorController.addMedication("111-111-111","title", "fdsf"));

    }

    @Test
    public void removePatientMedication_Fail() {
        doThrow(NoSuchElementException.class).when(doctorService).removePatientMedication(any(),any());

        assertEquals(ResponseEntity.badRequest().build(),doctorController.deleteMedication("111-111-111",UUID.randomUUID()));
    }
}
