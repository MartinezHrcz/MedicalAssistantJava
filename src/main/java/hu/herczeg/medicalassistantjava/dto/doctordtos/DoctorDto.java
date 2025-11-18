package hu.herczeg.medicalassistantjava.dto.doctordtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    public long id;
    public String name;
    public String address;
    public String phoneNumber;
    public String email;
}

