package org.example.web;

import org.example.dto.TreatmentCreateDto;
import org.example.dto.TreatmentResponseDto;
import org.example.dto.TreatmentUpdateDto;
import org.example.mapper.TreatmentMapper;
import org.example.service.TreatmentService;
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
@RequestMapping("/treatments")
public class TreatmentController {
    private final TreatmentService treatmentService;
    private final TreatmentMapper treatmentMapper;

    @Autowired
    public TreatmentController(TreatmentService treatmentService, TreatmentMapper treatmentMapper) {
        this.treatmentService = treatmentService;
        this.treatmentMapper = treatmentMapper;
    }

    @PostMapping
    public TreatmentResponseDto saveTreatmentData(@RequestBody TreatmentCreateDto treatmentCreateDto) {
        return treatmentService.saveTreatment(treatmentMapper.toTreatment(treatmentCreateDto));
    }

    @GetMapping(value = "/{id}")
    public TreatmentResponseDto getTreatmentData(@PathVariable("id") int id) {
        return treatmentService.findTreatmentById(id);
    }

    @PutMapping(value = "/{id}")
    public TreatmentResponseDto updateTreatmentData(@PathVariable("id") int id, @RequestBody TreatmentUpdateDto treatmentUpdateDto) {
        return treatmentService.updateTreatment(id, treatmentMapper.toTreatment(treatmentUpdateDto));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTreatmentData(@PathVariable("id") int id) {
        treatmentService.deleteTreatment(id);
    }

}
