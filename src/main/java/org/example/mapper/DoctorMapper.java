package org.example.mapper;

import org.example.dto.DoctorCreateDto;
import org.example.dto.DoctorResponseDto;
import org.example.dto.DoctorUpdateDto;
import org.example.model.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor toDoctor(DoctorCreateDto dto);

    Doctor toDoctor(DoctorUpdateDto dto);

    DoctorResponseDto toDoctorResponseDto(Doctor doctor);
}
