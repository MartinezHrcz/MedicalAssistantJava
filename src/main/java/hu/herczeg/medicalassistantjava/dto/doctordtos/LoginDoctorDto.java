package hu.herczeg.medicalassistantjava.dto.doctordtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginDoctorDto {
    @Email
    public String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password should be atleast 8 characters long,"
                    + " have atleast 1 uppercase 1 lowercase and a special character")
    public String password;
}

