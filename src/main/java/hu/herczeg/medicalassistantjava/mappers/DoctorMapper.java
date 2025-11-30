package hu.herczeg.medicalassistantjava.mappers;

import hu.herczeg.medicalassistantjava.dto.doctordtos.DoctorDto;
import hu.herczeg.medicalassistantjava.dto.doctordtos.RegisterDoctorDto;
import hu.herczeg.medicalassistantjava.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    DoctorDto toDto(Doctor doctor);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "passwordHash")
    Doctor toEntity(RegisterDoctorDto dto);

    List<DoctorDto> toDtos(List<Doctor> doctors);
}
