package org.example.dto;

import org.example.model.Gender;

public class PatientCreateDto {
    private String firstName;
    private String lastName;
    private Gender gender;
    private int doctorId;
    private int diagnosisId;

    public PatientCreateDto() {
    }

    public PatientCreateDto(String firstName, String lastName, String gender, int doctorId, int diagnosisId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = Gender.valueOf(gender);
        this.doctorId = doctorId;
        this.diagnosisId = diagnosisId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
