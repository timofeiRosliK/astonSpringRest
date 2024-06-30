package org.example.mapper;

import org.example.dto.TreatmentCreateDto;
import org.example.dto.TreatmentResponseDto;
import org.example.dto.TreatmentUpdateDto;
import org.example.model.Treatment;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(uses = DiagnosisMapper.class, componentModel = "spring")
public interface TreatmentMapper {
    Treatment toTreatment(TreatmentCreateDto dto);

    Treatment toTreatment(TreatmentUpdateDto dto);

    TreatmentResponseDto toTreatmentResponseDto(Treatment treatment);

    Set<TreatmentResponseDto> toTreatmentDtoListResponse(Set<Treatment> treatments);
}
