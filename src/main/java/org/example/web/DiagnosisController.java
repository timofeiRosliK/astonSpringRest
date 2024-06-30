package org.example.web;

import org.example.dto.DiagnosisCreateDto;
import org.example.dto.DiagnosisResponseDto;
import org.example.dto.DiagnosisUpdateDto;
import org.example.mapper.DiagnosisMapper;
import org.example.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diagnoses")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;
    private final DiagnosisMapper diagnosisMapper;

    @Autowired
    public DiagnosisController(DiagnosisService diagnosisService, DiagnosisMapper diagnosisMapper) {
        this.diagnosisService = diagnosisService;
        this.diagnosisMapper = diagnosisMapper;
    }

    @PostMapping
    public DiagnosisResponseDto saveDiagnosisData(@RequestBody DiagnosisCreateDto diagnosisCreateDto) {
        return diagnosisService.saveDiagnosis(diagnosisMapper.toDiagnosis(diagnosisCreateDto),
                diagnosisCreateDto.getTreatmentId());

    }

    @GetMapping(value = "/{id}")
    public DiagnosisResponseDto getDiagnosisById(@PathVariable("id") int id) {
        return diagnosisService.getDiagnosisById(id);
    }

    @PutMapping(value = "/{id}")
    public DiagnosisResponseDto updateDiagnosisData(@PathVariable("id") int id, @RequestBody DiagnosisUpdateDto diagnosisUpdateDto) {
        return diagnosisService.updateDiagnosis(id, diagnosisMapper.toDiagnosis(diagnosisUpdateDto));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDiagnosisData(@PathVariable("id") int id) {
        diagnosisService.removeDiagnosis(id);
    }

}
