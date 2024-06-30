package org.example.service;

import org.example.config.TestContainerConfig;
import org.example.dto.DiagnosisResponseDto;
import org.example.exceptions.DiagnosisNotFoundException;
import org.example.exceptions.TreatmentNotFoundException;
import org.example.model.Diagnosis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContainerConfig.class})
@WebAppConfiguration
@Sql(scripts = {"classpath:insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:delete.sql"}, executionPhase = AFTER_TEST_METHOD)

class DiagnosisServiceTest {

    @Autowired
    private DiagnosisService diagnosisService;

    @Test
    void saveDiagnosis_WhenTreatmentIsNull_TreatmentNotFoundException() {
        int treatmentId = 500;

        Diagnosis diagnosis = new Diagnosis(1, "cancer");

        assertThrows(TreatmentNotFoundException.class, () -> diagnosisService.saveDiagnosis(diagnosis, treatmentId));
    }

    @Test
    void saveDiagnosis_WhenSaveTreatment_TreatmentSaved() {
        int treatmentId = 1;

        Diagnosis diagnosis = new Diagnosis(2, "flu");
        DiagnosisResponseDto savedDiagnosis = diagnosisService.saveDiagnosis(diagnosis, treatmentId);

        Assertions.assertEquals(2, savedDiagnosis.getDiagnosisId());
        Assertions.assertEquals("flu", savedDiagnosis.getName());
    }

    @Test
    void getDiagnosis_WhenGetDiagnosis_DiagnosisGot() {
        int diagnosisId = 3;

        DiagnosisResponseDto diagnosis = diagnosisService.getDiagnosisById(diagnosisId);

        Assertions.assertEquals(3, diagnosis.getDiagnosisId());
        Assertions.assertEquals("Diabetes", diagnosis.getName());
    }

    @Test
    void getDiagnosis_WhenDiagnosisGet_ThrowDiagnosisNotFoundException() {
        int diagnosisId = 500;

        assertThrows(DiagnosisNotFoundException.class, () ->diagnosisService.getDiagnosisById(diagnosisId) );
    }

    @Test
    void updateDiagnosis_WhenUpdateDiagnosis_DiagnosisUpdated(){
        int diagnosisId = 2;
        Diagnosis diagnosis = new Diagnosis(diagnosisId, "cold");

        DiagnosisResponseDto updatedDiagnosis = diagnosisService.updateDiagnosis(diagnosisId, diagnosis);

        Assertions.assertEquals(2, updatedDiagnosis.getDiagnosisId());
        Assertions.assertEquals("cold", updatedDiagnosis.getName());
    }

    @Test
    void updateDiagnosis_WhenDiagnosisIsNull_ThrowDiagnosisNotFoundException(){
        int diagnosisId = 501;

        assertThrows(DiagnosisNotFoundException.class, () ->diagnosisService.getDiagnosisById(diagnosisId) );
    }

    @Test
    void deleteDiagnosis_WhenDeleteDiagnosis_DiagnosisDeleted(){
        int diagnosisId = 1;

        diagnosisService.removeDiagnosis(diagnosisId);

        assertThrows(DiagnosisNotFoundException.class, ()->diagnosisService.getDiagnosisById((diagnosisId)));
    }

}

