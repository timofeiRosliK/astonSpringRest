package org.example.service;

import org.example.config.TestContainerConfig;
import org.example.dto.TreatmentResponseDto;
import org.example.exceptions.TreatmentNotFoundException;
import org.example.model.Treatment;
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
class TreatmentServiceTest {

    @Autowired
    private TreatmentService treatmentService;

    @Test
    void saveTreatment_WhenSaveTreatment_TreatmentSaved() {

        Treatment treatment = new Treatment(1, "drugs");
        TreatmentResponseDto savedTreatment = treatmentService.saveTreatment(treatment);

        Assertions.assertEquals(1, savedTreatment.getTreatmentId());
        Assertions.assertEquals("drugs", savedTreatment.getName());
    }

    @Test
    void findTreatment_WhenFindTreatment_TreatmentGot() {
        int treatmentId = 3;

        TreatmentResponseDto treatment = treatmentService.findTreatmentById(treatmentId);

        Assertions.assertEquals(3, treatment.getTreatmentId());
        Assertions.assertEquals("Insulin", treatment.getName());
    }

    @Test
    void findTreatment_WhenTreatmentFind_ThrowTreatmentNotFoundException() {
        int treatmentId = 500;

        assertThrows(TreatmentNotFoundException.class, () ->treatmentService.findTreatmentById(treatmentId) );
    }

    @Test
    void updateTreatment_WhenUpdateTreatment_TreatmentUpdated(){
        int treatmentId = 2;
        Treatment treatment = new Treatment(treatmentId, "sleep");

        TreatmentResponseDto updatedTreatment = treatmentService.updateTreatment(treatmentId, treatment);

        Assertions.assertEquals(2, updatedTreatment.getTreatmentId());
        Assertions.assertEquals("sleep", updatedTreatment.getName());
    }

    @Test
    void updateTreatment_WhenTreatmentIsNull_ThrowTreatmentNotFoundException(){
        int treatmentId = 501;

        assertThrows(TreatmentNotFoundException.class, () ->treatmentService.findTreatmentById(treatmentId) );
    }

    @Test
    void deleteTreatment_WhenDeleteTreatment_TreatmentDeleted(){
        int treatmentId = 1;

        treatmentService.deleteTreatment(treatmentId);

        assertThrows(TreatmentNotFoundException.class, ()->treatmentService.findTreatmentById(treatmentId));
    }

}