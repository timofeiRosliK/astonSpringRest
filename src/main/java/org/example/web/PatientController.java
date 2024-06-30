package org.example.web;

import org.example.dto.PatientCreateDto;
import org.example.dto.PatientResponseDto;
import org.example.dto.PatientUpdateDto;
import org.example.mapper.PatientMapper;
import org.example.service.PatientService;
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
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientController(PatientService patientService, PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @PostMapping
    public PatientResponseDto savePatientData(@RequestBody PatientCreateDto patientCreateDto) {
        return patientService.savePatient(patientMapper.toPatient(patientCreateDto),
                patientCreateDto.getDoctorId(), patientCreateDto.getDiagnosisId());
    }

    @GetMapping(value = "/{id}")
    public PatientResponseDto getPatientData(@PathVariable("id") int id) {
        return patientService.getPatientById(id);
    }

    @PutMapping(value = "/{id}")
    public PatientResponseDto updatePatientData(@PathVariable("id") int id, @RequestBody PatientUpdateDto patientUpdateDto) {
        return patientService.updatePatient(id, patientMapper.toPatientUpdateDto(patientUpdateDto));
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        patientService.removePatient(id);
    }

}
