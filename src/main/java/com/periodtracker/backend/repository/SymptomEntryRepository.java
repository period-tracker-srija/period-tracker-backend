package com.periodtracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.periodtracker.backend.model.SymptomEntry;

import java.util.*;

// @Repository 
public interface SymptomEntryRepository extends JpaRepository<SymptomEntry, Long> {
    List<SymptomEntry> findByDailyLogId(Long dailyLogId);
    void deleteByDailyLogId(Long dailyLogId);
}
