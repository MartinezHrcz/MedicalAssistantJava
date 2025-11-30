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
public class DoctorDto {
    public long id;
    public String name;
    public String address;
    public String phone;
    public String email;
}

