package com.periodtracker.backend.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class SymptomEntryResponse {
    private Long symptomTypeId;
    private String symptomTypeName;

    @JsonRawValue 
    private String value;

    public SymptomEntryResponse(Long symptomTypeId, String symptomTypeName, String value) {
        this.symptomTypeId = symptomTypeId;
        this.symptomTypeName = symptomTypeName;
        this.value = value;
    }

    public Long getSymptomTypeId() { return symptomTypeId; }
    public String getSymptomTypeName() { return symptomTypeName; }
    public String getValue() { return value; }
}
