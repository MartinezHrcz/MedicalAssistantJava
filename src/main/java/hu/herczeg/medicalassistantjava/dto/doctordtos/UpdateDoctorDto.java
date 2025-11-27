package hu.herczeg.medicalassistantjava.dto.doctordtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UpdateDoctorDto {
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[A-Za-zÀ-ž\\ \\s'-]{2,50}$",
            message = "Name should only contain letters, spaces or hypens!")
    public String name;
    @Nullable
    public String address;
    public String phone;
    @Email
    public String email;
}
