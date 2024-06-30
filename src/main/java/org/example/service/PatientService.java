package org.example.service;

import org.example.dto.PatientResponseDto;
import org.example.exceptions.DiagnosisNotFoundException;
import org.example.exceptions.DoctorNotFoundException;
import org.example.exceptions.PatientNotFoundException;
import org.example.mapper.PatientMapper;
import org.example.model.Diagnosis;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.repository.DiagnosisRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final DoctorRepository doctorRepository;
    private final DiagnosisRepository diagnosisRepository;


    @Autowired
    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper,
                          DoctorRepository doctorRepository, DiagnosisRepository diagnosisRepository) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.doctorRepository = doctorRepository;
        this.diagnosisRepository = diagnosisRepository;
    }


    @Transactional
    public PatientResponseDto savePatient(Patient patient, int doctorId, int diagnosisId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->
                new DoctorNotFoundException(String.format("Doctor with id %d is not found", doctorId)));
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow(() ->
                new DiagnosisNotFoundException(String.format("Diagnosis with id %d is not found", diagnosisId)));

        patient.setDoctor(doctor);
        patient.setDiagnosis(diagnosis);

        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toPatientResponseDto(savedPatient);
    }

    @Transactional
    public PatientResponseDto getPatientById(int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new PatientNotFoundException(String.format("Patient with id %d is not found", id)));
        return patientMapper.toPatientResponseDto(patient);
    }

    @Transactional
    public PatientResponseDto updatePatient(int id, Patient patient) {
        Patient patientFromDb = patientRepository.findById(id).orElseThrow(() ->
                new PatientNotFoundException(String.format("Patient with id %d is not found", id)));

        patientFromDb.setFirstName(patient.getFirstName());
        patientFromDb.setLastName(patient.getLastName());

        return patientMapper.toPatientResponseDto(patientRepository.save(patientFromDb));
    }

    @Transactional
    public void removePatient(int id) {
        patientRepository.deleteById(id);
    }
}
