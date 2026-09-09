package com.periodtracker.backend.model;

import java.time.LocalDate;

import jakarta.persistence.*;;

@Entity 
@Table(name = "daily_logs")
public class DailyLog {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private LocalDate logDate;

    @ManyToOne 
    @JoinColumn(name = "cycle_day_type_id")
    private CycleDayType cycleDayType;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public CycleDayType getCycleDayType() { return cycleDayType; }
    public void setCycleDayType(CycleDayType cycleDayType) { this.cycleDayType = cycleDayType; }
}
