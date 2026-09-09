package com.periodtracker.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class DailyLogRequest {
    private LocalDate logDate;
    private Long cycleDayTypeId;
    private List<SymptomEntryRequest> symptoms;

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public Long getCycleDayTypeId() { return cycleDayTypeId; }
    public void setCycleDayTypeId(Long cycleDayTypeId) { this.cycleDayTypeId = cycleDayTypeId; }

    public List<SymptomEntryRequest> getSymptoms() { return symptoms; }
    public void setSymptoms(List<SymptomEntryRequest> symptoms) { this.symptoms = symptoms; }
}
