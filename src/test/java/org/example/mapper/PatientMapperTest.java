package org.example.mapper;

import org.example.dto.PatientResponseDto;
import org.example.dto.PatientUpdateDto;
import org.example.dto.PatientCreateDto;
import org.example.model.Diagnosis;
import org.example.model.Doctor;
import org.example.model.Gender;
import org.example.model.Patient;
import org.example.model.Treatment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PatientMapperImpl.class,
        DoctorMapperImpl.class,
        DiagnosisMapperImpl.class,
        TreatmentMapperImpl.class
})

class PatientMapperTest {

    @Autowired
    private PatientMapper patientMapper;

    @Test
    void testToPatientFromCreateDto(){
        PatientCreateDto patientCreateDto = new PatientCreateDto();
        patientCreateDto.setFirstName("Ivan");
        patientCreateDto.setLastName("Ivanov");
        patientCreateDto.setGender(Gender.MAN);
        patientCreateDto.setDoctorId(1);
        patientCreateDto.setDiagnosisId(1);

        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setFirstName("Galya");
        doctor.setFirstName("Ivanova");
        doctor.setSpecialization("oncologist");

        Diagnosis diagnosis= new Diagnosis();
        diagnosis.setDiagnosisId((1));
        diagnosis.setName("cancer");

        Patient patient = patientMapper.toPatient(patientCreateDto);
        patient.setDoctor(doctor);
        patient.setDiagnosis(diagnosis);

        assertNotNull(patient);
        assertEquals(patientCreateDto.getFirstName(), patient.getFirstName());
        assertEquals(patientCreateDto.getLastName(), patient.getLastName());
        assertEquals(patientCreateDto.getGender(), patient.getGender());
        assertEquals(patientCreateDto.getDoctorId(), patient.getDoctor().getId());
        assertEquals(patientCreateDto.getDiagnosisId(), patient.getDiagnosis().getDiagnosisId());
    }

    @Test
    void testToPatientFromUpdateDto() {
        PatientUpdateDto patientUpdateDto = new PatientUpdateDto();
        patientUpdateDto.setFirstName("Katya");
        patientUpdateDto.setLastName("Petrova");
        patientUpdateDto.setGender(Gender.WOMAN);

        Patient patient = patientMapper.toPatient(patientUpdateDto);

        assertNotNull(patient);
        assertEquals("Katya", patient.getFirstName());
        assertEquals("Petrova", patient.getLastName());
        assertEquals(Gender.WOMAN, patient.getGender());
    }

    @Test
    void testToPatientResponseDtoFromPatient(){
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setFirstName("Galya");
        doctor.setFirstName("Ivanova");
        doctor.setSpecialization("oncologist");

        Treatment drugs = new Treatment(1, "drugs");
        Treatment remedies = new Treatment(2, "remedies");

        Set<Treatment> treatments = new HashSet<>();
        treatments.add(drugs);
        treatments.add(remedies);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId(1);
        diagnosis.setName("cold");
        diagnosis.setTreatments(treatments);

        Patient patient = new Patient();

        patient.setId(1);
        patient.setFirstName("Valya");
        patient.setLastName("Doroshko");
        patient.setDoctor(doctor);
        patient.setDiagnosis(diagnosis);

        PatientResponseDto dto = patientMapper.toPatientResponseDto(patient);

        assertNotNull(dto);
        assertEquals(patient.getFirstName(), dto.getFirstName());
        assertEquals(patient.getLastName(), dto.getLastName());
        assertEquals(patient.getGender(), dto.getGender());
        assertEquals(patient.getDoctor().getFirstName(), dto.getDoctor().getFirstName());
        assertEquals(patient.getDiagnosis().getName(), dto.getDiagnosis().getName());
        assertEquals(patient.getDiagnosis().getTreatments().size(), dto.getDiagnosis().getTreatments().size());
    }

}
