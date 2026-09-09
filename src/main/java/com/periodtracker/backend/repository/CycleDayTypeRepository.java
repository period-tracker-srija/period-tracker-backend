package com.periodtracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.periodtracker.backend.model.CycleDayType;

import java.util.List;

// @Repository 
public interface CycleDayTypeRepository extends JpaRepository<CycleDayType, Long> {
    List<CycleDayType> findAllByOrderByDisplayOrderAsc();
}
