package hu.herczeg.medicalassistantjava.service;


import hu.herczeg.medicalassistantjava.model.Patient;
import hu.herczeg.medicalassistantjava.repository.PatientRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientServiceTests {

    @Autowired
    private PatientService patientService;

    @MockitoBean
    private PatientRepository patientRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setup() {

    }

    @Test
    public void getAllPatients_WhenNotEmpty_ReturnAll() {

    }

    @Test
    public void validId_GetById_ReturnPatient() {

    }

    @Test
    public void invalidId_GetById_Fail() {

    }

    @Test
    public void validData_createPatient_ReturnPatient() {

    }

    @Test
    public void validId_UpdateDoctor_Success(){

    }

    @Test
    public void invalidId_UpdateDoctor_Fail() {

    }

    @Test
    public void pwdMatch_UpdatePwd_Success() {

    }

    @Test
    public void pwdNoMatch_UpdatePwd_Fail() {

    }

    @Test
    public void invalidId_UpdatePwd_Fail() {

    }

    @Test
    public void validId_DeletePatient_Success() {

    }

    @Test
    public void invalidId_DeletePatient_Fail() {

    }

    @Test
    public void invalidPassword_LoginPatient_Fail() {

    }

    @Test
    public void invalidTaj_LoginPatient_Fail() {

    }

    @Test
    public void invalidPassword_LoginPatient_Fail() {

    }
}
