package org.example.mapper;

import org.example.dto.DiagnosisCreateDto;
import org.example.dto.DiagnosisResponseDto;
import org.example.dto.DiagnosisUpdateDto;
import org.example.model.Diagnosis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = TreatmentMapper.class, componentModel = "spring")
public interface DiagnosisMapper {

    Diagnosis toDiagnosis(DiagnosisCreateDto dto);

    Diagnosis toDiagnosis(DiagnosisUpdateDto dto);

    @Mapping(source = "treatments", target = "treatments")
    DiagnosisResponseDto toDiagnosisResponseDto(Diagnosis diagnosis);
}
