package org.example.service;


import org.example.config.TestContainerConfig;
import org.example.dto.PatientResponseDto;
import org.example.exceptions.DiagnosisNotFoundException;
import org.example.exceptions.DoctorNotFoundException;
import org.example.exceptions.PatientNotFoundException;
import org.example.model.Gender;
import org.example.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContainerConfig.class})
@WebAppConfiguration
@Sql(scripts = {"classpath:insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:delete.sql"}, executionPhase = AFTER_TEST_METHOD)
class PatientServiceTest {

    @Autowired
    private PatientService patientService;


    @Test
    void savePatient_WhenSavePatient_PatientSaved() {
        int doctorId = 1;
        int diagnosisId = 1;

        Patient patient = new Patient("Ivan", "Ivanov", Gender.MAN);
        patient.setId(1);

        PatientResponseDto savedPatient = patientService.savePatient(patient, doctorId, diagnosisId);

        assertNotNull(savedPatient);
        assertEquals("Ivan", savedPatient.getFirstName());
        assertEquals(1, savedPatient.getDoctor().getId());
        assertEquals(1, savedPatient.getDiagnosis().getDiagnosisId());
    }

    @Test
    void savePatient_WhenDoctorIsNull_ThrowDoctorNotFoundException(){
        int doctorId = 500;
        int diagnosisId = 1;

        Patient patient = new Patient("Ivan", "Ivanov", Gender.MAN);
        patient.setId(3);


        assertThrows(DoctorNotFoundException.class, ()-> patientService.savePatient(patient, doctorId, diagnosisId));
    }


    @Test
    void savePatient_WhenDiagnosisIsNull_ThrowDiagnosisNotFoundException(){
        int doctorId = 2;
        int diagnosisId = 500;

        Patient patient = new Patient("Ivan", "Ivanov", Gender.MAN);
        patient.setId(4);


        assertThrows(DiagnosisNotFoundException.class, ()-> patientService.savePatient(patient, doctorId, diagnosisId));
    }

    @Test
    void getPatient_WhenPatientIsNull_ThrowPatientNotFoundException(){
        int patientId = 500;

        assertThrows(PatientNotFoundException.class, ()-> patientService.getPatientById(patientId));
    }

    @Test
    void getPatient_WhenGetPatient_PatientGot(){
        int patientId = 1;
        PatientResponseDto patient = patientService.getPatientById(patientId);

        assertNotNull(patient);
        assertEquals("Alice", patient.getFirstName());
        assertEquals("Johnson", patient.getLastName());
        assertEquals("WOMAN", patient.getGender().name());
        assertEquals(1, patient.getDoctor().getId());
        assertEquals(1, patient.getDiagnosis().getDiagnosisId());
    }

    @Test
    void updatePatient_WhenPatientIsNull_ThrowPatientNotFoundException(){
        int patientId = 500;

        assertThrows(PatientNotFoundException.class, ()-> patientService.getPatientById(patientId));
    }

    @Test
    void updatePatient_WhenUpdatePatient_PatientUpdate(){
        int patientId = 3;
        Patient patient = new Patient(patientId, "Ivan", "Ivanov");

        PatientResponseDto updatedPatient = patientService.updatePatient(patientId, patient);

        assertNotNull(patient);
        assertEquals("Ivan", updatedPatient.getFirstName());
        assertEquals("Ivanov", updatedPatient.getLastName());
    }

    @Test
    void deletePatient_WhenDeletePatient_PatientDeleted(){
        int patientId = 2;

        patientService.removePatient(patientId);

        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(patientId));
    }

}