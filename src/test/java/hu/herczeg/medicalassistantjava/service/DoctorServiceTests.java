package hu.herczeg.medicalassistantjava.service;

import hu.herczeg.medicalassistantjava.dto.doctordtos.RegisterDoctorDto;
import hu.herczeg.medicalassistantjava.model.Doctor;
import hu.herczeg.medicalassistantjava.repository.DoctorRepository;
import hu.herczeg.medicalassistantjava.service.interfaces.DoctorService;
import lombok.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DoctorServiceTests {

    @MockitoBean
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorService doctorService;

    Doctor doctor = new Doctor(1L,"testtest","test","123","test@gmail.com","xyzzzzz");

    @BeforeEach
    public void setup() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any())).thenReturn(doctor);
    }

    @Test
    public void getAllDoctors_WhenNotEmpty_ReturnAll(){
        Assertions.assertNotNull(doctorService.getAllDoctors());
    }
}
