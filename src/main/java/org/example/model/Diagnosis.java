package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id")
    private int diagnosisId;
    @Column(name = "diagnosis_name")
    private String name;
    @OneToMany(mappedBy = "diagnosis", fetch = FetchType.EAGER)
    private List<Patient> patients;
    @ManyToMany(mappedBy = "diagnoses", fetch = FetchType.EAGER)
    private Set<Treatment> treatments;

    public Diagnosis() {
    }

    public Diagnosis(String name, List<Patient> patients) {
        this.name = name;
        this.patients = patients;
        this.treatments = new HashSet<>();
    }

    public Diagnosis(String name) {
        this.name = name;
    }

    public Diagnosis(int diagnosisId, String name) {
        this.diagnosisId = diagnosisId;
        this.name = name;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
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

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    public void addTreatment(Treatment treatment) {
        if (treatments == null) {
            treatments = new HashSet<>();
        }

        treatments.add(treatment);
        treatment.getDiagnoses().add(this);
    }

}
