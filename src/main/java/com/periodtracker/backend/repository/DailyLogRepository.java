package com.periodtracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.periodtracker.backend.model.DailyLog;

import java.time.LocalDate;
import java.util.Optional;

// @Repository 
public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
    Optional<DailyLog> findByLogDate(LocalDate logDate);
    void deleteByLogDate(LocalDate logDate);
}
