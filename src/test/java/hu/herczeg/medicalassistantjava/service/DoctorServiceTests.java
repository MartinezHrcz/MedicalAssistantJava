package hu.herczeg.medicalassistantjava.service;

import hu.herczeg.medicalassistantjava.dto.doctordtos.RegisterDoctorDto;
import hu.herczeg.medicalassistantjava.model.Doctor;
import hu.herczeg.medicalassistantjava.repository.DoctorRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.DoctorService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DoctorServiceTests {

    @MockitoBean
    private DoctorRepository doctorRepository;

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
}