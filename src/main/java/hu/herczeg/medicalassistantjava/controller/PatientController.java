package hu.herczeg.medicalassistantjava.controller;

import hu.herczeg.medicalassistantjava.dto.common.PasswordUpdateDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientMedicationDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientLoginDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.RegisterPatientDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.PatientAuthResponseDto;
import hu.herczeg.medicalassistantjava.dto.patientdtos.UpdatePatientDto;
import hu.herczeg.medicalassistantjava.service.interfaces.PatientService;
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

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAll() {
        return ResponseEntity.ok(patientService.GetAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(patientService.GetPatientById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<List<PatientDto>> getByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(patientService.GetPatientByName(name));
    }

    @GetMapping("/bytaj/{taj}")
    public ResponseEntity<PatientDto> getBytaj(@PathVariable("taj") String taj) {
        try {
            return ResponseEntity.ok(patientService.GetPatientByTaj(taj));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PatientDto> create(@RequestBody RegisterPatientDto registerPatientDto) {
        try {
            return ResponseEntity.ok(patientService.CreatePatient(registerPatientDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<PatientDto> update(@PathVariable long id,@RequestBody UpdatePatientDto updatePatientDto) {
        try {
            return ResponseEntity.ok(patientService.UpdatePatient(id,updatePatientDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("pwdupdate/{id}")
    public ResponseEntity updatePwd(@PathVariable long id, @RequestBody PasswordUpdateDto dto) {
        try {
            return patientService.UpdatePatientPassword(id, dto)
                    ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            patientService.DeletePatient(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<PatientAuthResponseDto> login(@RequestBody PatientLoginDto dto) {
        try {
            return ResponseEntity.ok(patientService.LoginPatientAsync(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/medication/{taj}")
    public ResponseEntity<PatientMedicationDto> getMedication(@PathVariable String taj) {
        try {
            return ResponseEntity.ok(patientService.GetPatientMedication(taj));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}