package com.periodtracker.backend.model;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.*;

@Entity
@Table(name = "symptom_entries")
public class SymptomEntry {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "daily_log_id")
    private DailyLog dailyLog;

    @ManyToOne 
    @JoinColumn(name = "symptom_type_id")
    private SymptomType symptomType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String value;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DailyLog getDailyLog() { return dailyLog; }
    public void setDailyLog(DailyLog dailyLog) { this.dailyLog = dailyLog; }

    public SymptomType getSymptomType() { return symptomType; }
    public void setSymptomType(SymptomType symptomType) { this.symptomType = symptomType; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
