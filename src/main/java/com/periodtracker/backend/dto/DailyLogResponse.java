package com.periodtracker.backend.dto;

import java.time.LocalDate;
import java.util.List;

import com.periodtracker.backend.model.CycleDayType;

public class DailyLogResponse {
    private LocalDate logDate;
    private CycleDayType cycleDayType;
    private List<SymptomEntryResponse> symptoms;

    public DailyLogResponse(LocalDate logDate, CycleDayType cycleDayType, List<SymptomEntryResponse> symptoms) {
        this.logDate = logDate;
        this.cycleDayType = cycleDayType;
        this.symptoms = symptoms;
    }

    public LocalDate getLogDate() { return logDate; }
    public CycleDayType getCycleDayType() { return cycleDayType; }
    public List<SymptomEntryResponse> getSymptoms() { return symptoms; }
}
