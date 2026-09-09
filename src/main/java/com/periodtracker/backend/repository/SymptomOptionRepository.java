package com.periodtracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.periodtracker.backend.model.SymptomOption;

import java.util.List;

// @Repository 
public interface SymptomOptionRepository extends JpaRepository<SymptomOption, Long> {
    List<SymptomOption> findBySymptomTypeId(Long SymptomTypeId);
}
