package com.periodtracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.periodtracker.backend.model.DailyLog;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

// @Repository 
public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
    Optional<DailyLog> findByLogDate(LocalDate logDate);    // spring builds these from their names
    void deleteByLogDate(LocalDate logDate);
    List<DailyLog> findByCycleDayTypeId(Long cycleDayTypeId);
}
