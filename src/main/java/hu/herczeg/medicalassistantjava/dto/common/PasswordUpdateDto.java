package hu.herczeg.medicalassistantjava.dto.common;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDto {
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password should be atleast 8 characters long,"
                    + " have atleast 1 uppercase 1 lowercase and a special character")
    public String oldPassword;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password should be atleast 8 characters long,"
                    + " have atleast 1 uppercase 1 lowercase and a special character")
    public String newPassword;
}
