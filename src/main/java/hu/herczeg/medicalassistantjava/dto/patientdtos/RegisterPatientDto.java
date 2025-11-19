package hu.herczeg.medicalassistantjava.dto.patientdtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientDto{
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[A-Za-zÀ-ž\\ \\s'-]{2,50}$", message = "Name should only contain letters, spaces or hypens!")
    public String name;
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$", message = "TAJ must be in 000-000-000 format!")
    public String taj;
    @Nullable
    public String address;
    @Nullable
    public String complaints;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password should be atleast 8 characters long, have atleast 1 uppercase 1 lowercase and a special character")
    public String password;
}

