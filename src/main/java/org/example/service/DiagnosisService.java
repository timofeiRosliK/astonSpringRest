package org.example.service;

import org.example.dto.DiagnosisResponseDto;
import org.example.exceptions.DiagnosisNotFoundException;
import org.example.exceptions.TreatmentNotFoundException;
import org.example.mapper.DiagnosisMapper;
import org.example.mapper.TreatmentMapper;
import org.example.model.Diagnosis;
import org.example.model.Patient;
import org.example.model.Treatment;
import org.example.repository.DiagnosisRepository;
import org.example.repository.PatientRepository;
import org.example.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    private final TreatmentRepository treatmentRepository;
    private final DiagnosisMapper diagnosisMapper;
    private final PatientRepository patientRepository;

    @Autowired
    public DiagnosisService(DiagnosisRepository diagnosisRepository,
                            DiagnosisMapper diagnosisMapper, TreatmentRepository treatmentRepository, PatientRepository patientRepository, TreatmentMapper treatmentMapper) {
        this.diagnosisRepository = diagnosisRepository;
        this.diagnosisMapper = diagnosisMapper;
        this.treatmentRepository = treatmentRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public DiagnosisResponseDto saveDiagnosis(Diagnosis diagnosis, int treatmentId) {
        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new TreatmentNotFoundException(String.format("Treatment with id %d is not found", treatmentId)));

        diagnosis.addTreatment(treatment);

        return diagnosisMapper.toDiagnosisResponseDto(diagnosisRepository.save(diagnosis));
    }

    @Transactional
    public DiagnosisResponseDto getDiagnosisById(int id) {
        return diagnosisMapper.toDiagnosisResponseDto(diagnosisRepository.findById(id).orElseThrow(() ->
                new DiagnosisNotFoundException(String.format("Diagnosis with id %d is not found", id))));
    }

    @Transactional
    public DiagnosisResponseDto updateDiagnosis(int id, Diagnosis diagnosis) {
        Diagnosis diagnosisFromDb = diagnosisRepository.findById(id).orElseThrow(() ->
                new DiagnosisNotFoundException(String.format("Diagnosis with id %d is not found", id)));
        diagnosisFromDb.setName(diagnosis.getName());

        return diagnosisMapper.toDiagnosisResponseDto(diagnosisRepository.save(diagnosisFromDb));
    }

    @Transactional
    public void removeDiagnosis(int id) {
        Diagnosis removedDiagnosis = diagnosisRepository.findById(id).
                orElseThrow(() -> new DiagnosisNotFoundException(String.format("Diagnosis with id %d is not found", id)));

        Set<Treatment> treatments = removedDiagnosis.getTreatments();
        for (Treatment treatment : treatments) {
            Set<Diagnosis> diagnoses = treatment.getDiagnoses();
            if (diagnoses != null) {
                diagnoses.remove(removedDiagnosis);
                treatmentRepository.save(treatment);
            }
        }

        List<Patient> patients = removedDiagnosis.getPatients();
        for (Patient patient : patients) {
            patient.setDiagnosis(null);
            patientRepository.save(patient);
        }
        diagnosisRepository.deleteById(id);
    }
}
