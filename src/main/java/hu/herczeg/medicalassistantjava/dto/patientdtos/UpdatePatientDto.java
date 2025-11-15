package hu.herczeg.medicalassistantjava.dto.patientdtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UpdatePatientDto{
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[A-Za-zÀ-ž\\ \\s'-]{2,50}$", message = "Name should only contain letters, spaces or hypens!")
    public String Name;
    @Nullable
    public String Address;
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$", message = "TAJ must be in 000-000-000 format!")
    public String Taj;
    public String Complaints;
}

