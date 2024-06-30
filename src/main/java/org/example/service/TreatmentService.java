package org.example.service;

import org.example.dto.TreatmentResponseDto;
import org.example.exceptions.TreatmentNotFoundException;
import org.example.mapper.TreatmentMapper;
import org.example.model.Diagnosis;
import org.example.model.Treatment;
import org.example.repository.DiagnosisRepository;
import org.example.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;
    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    public TreatmentService(TreatmentRepository treatmentRepository, TreatmentMapper treatmentMapper, DiagnosisRepository diagnosisRepository) {
        this.treatmentRepository = treatmentRepository;
        this.treatmentMapper = treatmentMapper;
        this.diagnosisRepository = diagnosisRepository;
    }

    public TreatmentResponseDto saveTreatment(Treatment treatment) {
        return treatmentMapper.toTreatmentResponseDto(treatmentRepository.save(treatment));
    }

    public TreatmentResponseDto findTreatmentById(int id) {
        Treatment treatment = treatmentRepository.findById(id).orElseThrow(() -> new
                TreatmentNotFoundException(String.format("Treatment with id %d is not found", id)));
        return treatmentMapper.toTreatmentResponseDto(treatment);
    }

    public TreatmentResponseDto updateTreatment(int id, Treatment treatment) {
        Treatment treatmentFromDb = treatmentRepository.findById(id).orElseThrow(() -> new
                TreatmentNotFoundException(String.format("Treatment with id %d is not found", id)));

        treatmentFromDb.setName(treatment.getName());

        return treatmentMapper.toTreatmentResponseDto(treatmentRepository.save(treatmentFromDb));
    }

    public void deleteTreatment(int id) {
        Treatment treatment = treatmentRepository.findById(id).orElseThrow(() -> new
                TreatmentNotFoundException(String.format("Treatment with id %d is not found", id)));

        Set<Diagnosis> diagnoses = treatment.getDiagnoses();
        for (Diagnosis diagnosis : diagnoses) {
            Set<Treatment> treatments = diagnosis.getTreatments();
            if (treatments != null) {
                treatments.remove(treatment);
                diagnosisRepository.save(diagnosis);
            }
        }
        treatmentRepository.deleteById(id);
    }
}
