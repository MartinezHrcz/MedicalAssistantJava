package hu.herczeg.medicalassistantjava.dto.patientdtos;

import jakarta.validation.constraints.Pattern;
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

public class PatientLoginDto {
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$", message = "TAJ must be in 000-000-000 format!")
    public String taj;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password should be atleast 8 characters long,"
                    + " have atleast 1 uppercase 1 lowercase and a special character")
    public String password;
}

