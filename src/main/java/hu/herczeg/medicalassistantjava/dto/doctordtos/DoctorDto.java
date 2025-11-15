package hu.herczeg.medicalassistantjava.dto.doctordtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class DoctorDto {
    public int Id;
    public String Name;
    public String Address;
    public String Phone;
    public String Email;
}

