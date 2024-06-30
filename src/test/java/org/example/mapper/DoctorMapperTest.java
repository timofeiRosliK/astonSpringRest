package org.example.mapper;

import org.example.dto.DoctorCreateDto;
import org.example.dto.DoctorResponseDto;
import org.example.dto.DoctorUpdateDto;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DoctorMapperImpl.class,
})
class DoctorMapperTest {
    
    @Autowired
    private DoctorMapper doctorMapper;

    @Test
    void testToDoctorFromCreateDto() {
        DoctorCreateDto dto = new DoctorCreateDto();
        dto.setFirstName("Galya");
        dto.setFirstName("Ivanova");
        dto.setSpecialization("oncologist");

        Patient patient = new Patient();

        patient.setId(1);
        patient.setFirstName("Valya");
        patient.setLastName("Doroshko");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        Doctor doctor = doctorMapper.toDoctor(dto);
        doctor.setPatients(patients);

        assertNotNull(doctor);
        assertEquals(doctor.getFirstName(), dto.getFirstName());
        assertEquals(1, doctor.getPatients().size());
    }

    @Test
    void testToDoctorFromUpdateDto() {
        DoctorUpdateDto doctorUpdateDto = new DoctorUpdateDto();
        doctorUpdateDto.setFirstName("Katya");
        doctorUpdateDto.setLastName("Petrova");
        doctorUpdateDto.setSpecialization("GP");

        Doctor doctor = doctorMapper.toDoctor(doctorUpdateDto);

        assertNotNull(doctor);
        assertEquals("Katya", doctor.getFirstName());
        assertEquals("Petrova", doctor.getLastName());
        assertEquals("GP", doctor.getSpecialization());
    }

    @Test
    void testToDoctorResponseDtoFromDiagnosis() {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setFirstName("Katya");
        doctor.setLastName("Petrova");
        doctor.setSpecialization("GP");

        DoctorResponseDto dto = doctorMapper.toDoctorResponseDto(doctor);

        assertNotNull(dto);
        assertEquals(doctor.getFirstName(), dto.getFirstName());
        assertEquals(doctor.getLastName(), dto.getLastName());
        assertEquals(doctor.getSpecialization(), dto.getSpecialization());

    }

}
