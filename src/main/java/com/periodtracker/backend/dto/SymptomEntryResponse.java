package com.periodtracker.backend.dto;

public class SymptomEntryResponse {
    private Long symptomTypeId;
    private String symptomTypeName;
    private Object value;

    public SymptomEntryResponse(Long symptomTypeId, String symptomTypeName, Object value) {
        this.symptomTypeId = symptomTypeId;
        this.symptomTypeName = symptomTypeName;
        this.value = value;
    }

    public Long getSymptomTypeId() { return symptomTypeId; }
    public String getSymptomTypeName() { return symptomTypeName; }
    public Object getValue() { return value; }
}
