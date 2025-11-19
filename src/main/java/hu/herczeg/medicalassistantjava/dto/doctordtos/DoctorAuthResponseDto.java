package hu.herczeg.medicalassistantjava.dto.doctordtos;

import hu.herczeg.medicalassistantjava.model.Doctor;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAuthResponseDto{
    public DoctorDto doctor;
    public String token;
}
