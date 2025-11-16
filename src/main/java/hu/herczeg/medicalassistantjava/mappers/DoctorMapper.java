package hu.herczeg.medicalassistantjava.mappers;
import hu.herczeg.medicalassistantjava.dto.doctordtos.DoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.RegisterDoctorDto;
import hu.herczeg.medicalassistantjava.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);

    Doctor toEnity(DoctorDto doctorDto);

    Doctor toEntity(RegisterDoctorDto dto);

    List<DoctorDto> toDtos(List<Doctor> doctors);
}
