package hu.herczeg.medicalassistantjava.dto.doctordtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAuthResponseDto {
    public DoctorDto doctor;
    public String token;
}
