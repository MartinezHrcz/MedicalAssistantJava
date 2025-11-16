package hu.herczeg.medicalassistantjava.mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ExerciseMapper.class})
public interface DoctorMapper {
}
