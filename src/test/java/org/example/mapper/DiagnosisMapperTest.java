package org.example.mapper;

import org.example.dto.DiagnosisCreateDto;
import org.example.dto.DiagnosisResponseDto;
import org.example.dto.DiagnosisUpdateDto;
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
        DiagnosisMapperImpl.class,
        TreatmentMapperImpl.class,
})
class DiagnosisMapperTest {

    @Autowired
    private DiagnosisMapper diagnosisMapper;

    @Test
    void testToDiagnosisFromCreateDto() {
        DiagnosisCreateDto dto = new DiagnosisCreateDto();
        dto.setName("cancer");
        dto.setTreatmentId(1);

        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setName("radiation therapy");

        Set<Treatment> treatments = new HashSet<>();
        treatments.add(treatment);

        Diagnosis diagnosis = diagnosisMapper.toDiagnosis(dto);
        diagnosis.setTreatments(treatments);

        assertNotNull(diagnosis);
        assertEquals(dto.getName(), diagnosis.getName());
        assertEquals(1, diagnosis.getTreatments().size());
    }

    @Test
    void testToDiagnosisFromUpdateDto() {
        DiagnosisUpdateDto diagnosisUpdateDto = new DiagnosisUpdateDto();
        diagnosisUpdateDto.setName("flu");

        Diagnosis diagnosis = diagnosisMapper.toDiagnosis(diagnosisUpdateDto);

        assertNotNull(diagnosis);
        assertEquals("flu", diagnosis.getName());
    }

    @Test
    void testToDiagnosisResponseDtoFromDiagnosis() {
        Treatment drugs = new Treatment(1, "drugs");
        Treatment remedies = new Treatment(2, "remedies");

        Set<Treatment> treatments = new HashSet<>();
        treatments.add(drugs);
        treatments.add(remedies);


        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId(1);
        diagnosis.setName("cold");
        diagnosis.setTreatments(treatments);

        DiagnosisResponseDto dto = diagnosisMapper.toDiagnosisResponseDto(diagnosis);

        assertNotNull(dto);
        assertEquals(diagnosis.getName(), dto.getName());
        assertEquals(2, dto.getTreatments().size());
    }

}
