package com.periodtracker.backend.model;

import jakarta.persistence.*;

@Entity 
@Table(name = "symptom_options")
public class SymptomOption {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "symptom_type_id")
    private SymptomType symptomType;

    private String label;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SymptomType getSymptomType() { return symptomType; }
    public void setSymptomType(SymptomType symptomType) { this.symptomType = symptomType; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}