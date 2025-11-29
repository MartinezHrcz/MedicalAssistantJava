package hu.herczeg.medicalassistantjava.controller;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientLoginDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.RegisterPatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.UpdatePatientDto;
import hu.herczeg.medicalassistantjava.service.interfaces.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientControllerTests {

    @Autowired
    private PatientController patientController;

    @MockitoBean
    private PatientService patientService;

    static PatientDto patient =
            new PatientDto(1L,
                    "testPatient",
                    "address",
                    "111-111-111",
                    "Complaints",
                    1L,
                    LocalDateTime.now()
            );

    @BeforeEach
    public void setUp() {
        when(patientService.getPatientById(anyLong())).thenReturn(patient);
        when(patientService.getAllPatients()).thenReturn(List.of(patient));
        when(patientService.getPatientByTaj(anyString())).thenReturn(patient);
    }

    @Test
    public void getAllPatients_WhenNotEmpty_ReturnAll() {
        assertEquals(ResponseEntity.ok().build().getClass(), patientController.getAll().getClass());
    }

    @Test
    public void validId_GetById_ReturnPatient() {
        var result = patientController.getById(1L);

        assertNotNull(result);
        assertEquals(patient, result.getBody());
    }

    @Test
    public void invalidId_GetById_Fail() {
        when(patientService.getPatientById(anyLong())).thenThrow(NoSuchElementException.class);

        assertEquals(ResponseEntity.badRequest().build().getClass(), patientController.getById(1L).getClass());
    }

    @Test
    public void validData_createPatient_ReturnPatient() {
        when(patientService.createPatient(any())).thenReturn(patient);
        RegisterPatientDto dto = RegisterPatientDto.builder()
                .name("testPatient")
                .taj("111-111-111")
                .complaints("Complaints")
                .password("New password")
                .build();

        var result = patientController.create(dto);

        assertNotNull(result);
        assertEquals(dto.getTaj(), result.getBody().getTaj());
    }

    @Test
    public void validId_UpdatePatient_Success(){
        UpdatePatientDto dto = UpdatePatientDto.builder()
                .name("testPatient")
                .taj("111-111-111")
                .complaints("Complaints")
                .address("new address").build();
        long updateId = 1L;

        patient.setName(dto.name);
        patient.setComplaints(dto.complaints);

        when(patientService.updatePatient(anyLong(), any(UpdatePatientDto.class))).thenReturn(patient);

        var result = patientController.update(1L, dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getBody().getName());
        assertEquals(dto.getComplaints(), result.getBody().getComplaints());
        verify(patientService, times(1)).updatePatient(anyLong(), any(UpdatePatientDto.class));
    }

    @Test
    public void invalidId_UpdatePatient_Fail() {
        UpdatePatientDto dto = UpdatePatientDto.builder()
                .name("testPatient")
                .taj("111-111-111")
                .complaints("Complaints")
                .address("new address").build();
        long updateId = 1L;
        when(patientService.updatePatient(any(),any())).thenThrow(NoSuchElementException.class);

        assertEquals(ResponseEntity.badRequest().build(), patientController.update(1L, dto));
    }

    @Test
    public void pwdMatch_UpdatePwd_Success() {
        when(patientService.updatePatientPassword(anyLong(), any(PasswordUpdateDto.class))).thenReturn(true);

        assertEquals(ResponseEntity.ok().build(), patientController.updatePwd(1L, new PasswordUpdateDto()));
    }

    @Test
    public void pwdNoMatch_UpdatePwd_Fail() {
        PasswordUpdateDto dto = new PasswordUpdateDto("Invalid password", "New password");
        when(patientService.updatePatientPassword(anyLong(), any(PasswordUpdateDto.class))).thenReturn(false);
        assertEquals(ResponseEntity.badRequest().build(), patientController.updatePwd(1L, dto));
    }

    @Test
    public void validId_DeletePatient_Success() {
        doNothing().when(patientService).deletePatient(anyLong());

        assertEquals(ResponseEntity.ok().build(), patientController.delete(1L));
    }

    @Test
    public void invalidId_DeletePatient_Fail() {
        doThrow(NoSuchElementException.class).when(patientService).deletePatient(anyLong());

        assertEquals(ResponseEntity.badRequest().build(), patientController.delete(1L));
    }

    @Test
    public void invalidPassword_LoginPatient_Fail() {
        when(patientService.loginPatientAsync(any(PatientLoginDto.class))).thenThrow(IllegalArgumentException.class);

        assertEquals(ResponseEntity.badRequest().build(), patientController.login(new PatientLoginDto()));
    }
}
