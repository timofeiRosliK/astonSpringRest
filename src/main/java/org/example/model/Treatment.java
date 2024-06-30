package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "treatments")
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_id")
    private int treatmentId;
    @Column(name = "treatment_name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "diagnoses_treatments",
            joinColumns = {@JoinColumn(name = "treatment_id")},
            inverseJoinColumns = {@JoinColumn(name = "diagnosis_id")}
    )
    private Set<Diagnosis> diagnoses ;

    public Treatment() {

    }

    public Treatment(String name) {
        this.name = name;
    }

    public Treatment(int treatmentId, String name) {
        this.treatmentId = treatmentId;
        this.name = name;
    }

    public Treatment(int treatmentId, String name, Set<Diagnosis> diagnoses) {
        this.treatmentId = treatmentId;
        this.name = name;
        this.diagnoses = diagnoses;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }

}