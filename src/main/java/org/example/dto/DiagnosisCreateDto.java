package org.example.dto;

public class DiagnosisCreateDto {
    private String name;
    private int treatmentId;

    public DiagnosisCreateDto() {
    }

    public DiagnosisCreateDto(String name, int treatmentId) {
        this.name = name;
        this.treatmentId = treatmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }
}
