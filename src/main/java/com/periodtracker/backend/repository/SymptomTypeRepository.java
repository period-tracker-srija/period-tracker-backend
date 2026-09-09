package com.periodtracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.periodtracker.backend.model.SymptomType;

import java.util.List;

// @Repository 
public interface SymptomTypeRepository extends JpaRepository<SymptomType, Long> {
    List<SymptomType> findByActiveTrueOrderByDisplayOrderAsc();
}
