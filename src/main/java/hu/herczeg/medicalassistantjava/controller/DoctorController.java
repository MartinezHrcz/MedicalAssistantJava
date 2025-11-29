package hu.herczeg.medicalassistantjava.controller;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.RegisterDoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.DoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.UpdateDoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.DoctorAuthResponseDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.LoginDoctorDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.service.interfaces.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/doctor")
@RestController
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(doctorService.getDoctorById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("byname/{name}")
    public ResponseEntity<List<DoctorDto>> getDoctorByName(@PathVariable String name) {
        return ResponseEntity.ok(doctorService.getDoctorByName(name));
    }

    @GetMapping("my_patients/{id}")
    public ResponseEntity<List<PatientDto>> getPatients(@PathVariable long id) {
        try {
            return ResponseEntity.ok(doctorService.getPatientsOfDoctor(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody RegisterDoctorDto doctorDto) {
        log.info("Creating");
        log.info("doctorDto={}", doctorDto.toString());
        try {
            return ResponseEntity.ok(doctorService.createDoctor(doctorDto));
        } catch (Exception e) {
            log.error("Exception={}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("pwd_update/{id}")
    public ResponseEntity updateDoctorPwd(@PathVariable long id, @RequestBody PasswordUpdateDto dto) {
        try {
            if (doctorService.updateDoctorPassword(id, dto)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable long id,@RequestBody UpdateDoctorDto doctorDto) {
        try {
            return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("addpatient/{doctorid}-{patientid}")
    public ResponseEntity addPatient(@PathVariable long doctorid, @PathVariable long patientid) {
        try {
            doctorService.addPatient(doctorid, patientid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<DoctorAuthResponseDto> loginDoctor(@RequestBody LoginDoctorDto dto) {
        try {
            return ResponseEntity.ok(doctorService.loginDoctor(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteDoctor(@PathVariable long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("removepatient/{doctorid}-{patientid}")
    public ResponseEntity removePatient(@PathVariable long doctorid, @PathVariable long patientid) {
        try {
            doctorService.removePatient(doctorid, patientid);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("medication/{patientTaj}_{title}_{medication}")
    public ResponseEntity addMedication(@PathVariable String patientTaj,
                                        @PathVariable String title,
                                        @PathVariable String medication) {
        try {
            doctorService.addPatientMedication(patientTaj,title,medication);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("medication/{patientTaj}_{medicationid}")
    public ResponseEntity deleteMedication(@PathVariable String patientTaj, @PathVariable UUID medicationid) {
        try {
            doctorService.removePatientMedication(patientTaj,medicationid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
