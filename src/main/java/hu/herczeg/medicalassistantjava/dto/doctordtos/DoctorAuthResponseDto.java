package hu.herczeg.medicalassistantjava.dto.doctordtos;

import hu.herczeg.medicalassistantjava.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class DoctorAuthResponseDto{
    public DoctorDto doctorDto;
    public String Token;
}
