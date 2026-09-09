package com.periodtracker.backend.dto;

public class SymptomEntryRequest {
    private Long symptomTypeId;
    private Object value;
    
    public Long getSymptomTypeId() { return symptomTypeId; }
    public void setSymptomTypeId(Long symptomTypeId) { this.symptomTypeId = symptomTypeId; }

    public Object getValue() { return value; }
    public void setValue(Object value) { this.value = value; }
}
