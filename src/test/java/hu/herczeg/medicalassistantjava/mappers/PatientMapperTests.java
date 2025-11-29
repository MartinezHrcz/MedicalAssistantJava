package hu.herczeg.medicalassistantjava.mappers;

import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientMedicationDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.RegisterPatientDto;
import hu.herczeg.medicalassistantjava.model.Doctor;
import hu.herczeg.medicalassistantjava.model.Medication;
import hu.herczeg.medicalassistantjava.model.Patient;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
public class PatientMapperTests {

    @Autowired
    private PatientMapper patientMapper;

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

    static RegisterPatientDto registerDto =
            RegisterPatientDto.builder()
                    .taj("111-111-111")
                    .name("testPatient")
                    .password("Pwd")
                    .address("address")
                    .complaints("Complaints").build();

    @Test
    public void validData_toDto_Success() {
        PatientDto result = patientMapper.toDto(patient);
        assertNotNull(result);
        assertEquals(patient.getId(), result.getId());
        assertEquals(patient.getAddress(), result.getAddress());
        assertEquals(patient.getComplaints(), result.getComplaints());
        assertEquals(patient.getName(), result.getName());
    }

    @Test
    public void null_toDto_Fail() {
        assertNull(patientMapper.toDto(null));
    }

    @Test
    public void validData_toEntity_Success() {
        Patient result = patientMapper.toEntity(registerDto);
        assertNotNull(result);
        assertEquals(patient.getAddress(), result.getAddress());
        assertEquals(patient.getComplaints(), result.getComplaints());
        assertEquals(patient.getName(), result.getName());
    }

    @Test
    public void null_toEntity_Fail() {
        assertNull(patientMapper.toEntity(null));
    }

    @Test
    public void validData_toMedication_Success() {
        patient.getMedications().add(new Medication(UUID.randomUUID(),"testTitle", "testDesc", patient));
        PatientMedicationDto result = patientMapper.toMedicationDto(patient);
        assertNotNull(result);
        assertEquals(patient.getId(), result.getId());
        assertEquals(patient.getTaj(), result.getTaj());
        assertEquals(patient.getMedications(), result.getMedications());
    }

    @Test
    public void null_toMedication_Fail() {
        assertNull(patientMapper.toMedicationDto(null));
    }
}
