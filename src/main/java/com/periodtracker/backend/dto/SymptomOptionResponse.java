package com.periodtracker.backend.dto;

public class SymptomOptionResponse {
    private Long id;
    private String label;

    public SymptomOptionResponse(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() { return id; }
    public String getLabel() { return label; }
}
