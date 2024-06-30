package org.example.service;

import org.example.dto.DoctorResponseDto;
import org.example.exceptions.DoctorNotFoundException;
import org.example.mapper.DoctorMapper;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final PatientRepository patientRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public DoctorResponseDto saveDoctor(Doctor doctor) {
        return doctorMapper.toDoctorResponseDto(doctorRepository.save(doctor));
    }

    @Transactional
    public DoctorResponseDto getDoctorById(int id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new DoctorNotFoundException(String.format("Diagnosis with id %d is not found", id)));
        return doctorMapper.toDoctorResponseDto(doctor);
    }

    @Transactional
    public DoctorResponseDto updateDoctorById(int id, Doctor doctor) {
        Doctor doctorFromDb = doctorRepository.findById(id).orElseThrow(() ->
                new DoctorNotFoundException(String.format("Doctor with id %d is not found", id)));

        doctorFromDb.setFirstName(doctor.getFirstName());
        doctorFromDb.setLastName(doctor.getLastName());
        doctorFromDb.setSpecialization(doctor.getSpecialization());

        return doctorMapper.toDoctorResponseDto(doctorRepository.save(doctorFromDb));
    }

    @Transactional
    public void removeDoctor(int id) {
        Doctor doctorFromDb = doctorRepository.findById(id).orElseThrow(() ->
                new DoctorNotFoundException(String.format("Doctor with id %d is not found", id)));

        List<Patient> patients = doctorFromDb.getPatients();
        for (Patient patient : patients) {
            patient.setDoctor(null);
            patientRepository.save(patient);
        }

        doctorRepository.deleteById(id);
    }
}

