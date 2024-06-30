package org.example.mapper;

import org.example.dto.DiagnosisCreateDto;
import org.example.dto.DiagnosisResponseDto;
import org.example.dto.DiagnosisUpdateDto;
import org.example.dto.TreatmentCreateDto;
import org.example.dto.TreatmentResponseDto;
import org.example.dto.TreatmentUpdateDto;
import org.example.model.Diagnosis;
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
        TreatmentMapperImpl.class,
        DiagnosisMapperImpl.class
})
class TreatmentMapperTest {

    @Autowired
    private TreatmentMapper treatmentMapper;

    @Test
    void testToTreatmentFromCreateDto() {
        TreatmentCreateDto dto = new TreatmentCreateDto();
        dto.setName("chemical therapy");

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId(1);
        diagnosis.setName("cancer");

        Set<Diagnosis> diagnoses = new HashSet<>();
        diagnoses.add(diagnosis);

        Treatment treatment = treatmentMapper.toTreatment(dto);
        treatment.setDiagnoses(diagnoses);

        assertNotNull(treatment);
        assertEquals(dto.getName(), treatment.getName());
        assertEquals(1, treatment.getDiagnoses().size());
    }

    @Test
    void testToTreatmentFromUpdateDto() {
        TreatmentUpdateDto treatmentUpdateDto = new TreatmentUpdateDto();
        treatmentUpdateDto.setName("surgery");

        Treatment treatment = treatmentMapper.toTreatment(treatmentUpdateDto);

        assertNotNull(treatment);
        assertEquals("surgery", treatment.getName());
    }

    @Test
    void testToTreatmentResponseDtoFromTreatment() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setName("cold");

        TreatmentResponseDto dto = treatmentMapper.toTreatmentResponseDto(treatment);

        assertNotNull(dto);
        assertEquals(treatment.getName(), dto.getName());
    }

}
