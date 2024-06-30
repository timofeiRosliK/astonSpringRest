package org.example.dto;

import java.util.Set;

public class DiagnosisResponseDto {
    private int diagnosisId;
    private String name;
    private Set<TreatmentResponseDto> treatments;

    public DiagnosisResponseDto() {
    }

    public DiagnosisResponseDto(int diagnosisId, String name, Set<TreatmentResponseDto> treatments) {
        this.diagnosisId = diagnosisId;
        this.name = name;
        this.treatments = treatments;
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TreatmentResponseDto> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<TreatmentResponseDto> treatments) {
        this.treatments = treatments;
    }

}
