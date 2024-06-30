package org.example.mapper;

import org.example.dto.PatientCreateDto;
import org.example.dto.PatientResponseDto;
import org.example.dto.PatientUpdateDto;
import org.example.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DoctorMapper.class, DiagnosisMapper.class}, componentModel = "spring")
public interface PatientMapper {
    Patient toPatient(PatientCreateDto dto);

    Patient toPatient(PatientUpdateDto dto);

    Patient toPatientUpdateDto(PatientUpdateDto dto);

    @Mapping(source = "doctor", target = "doctor")
    @Mapping(source = "diagnosis", target = "diagnosis")
    PatientResponseDto toPatientResponseDto(Patient patient);
}
