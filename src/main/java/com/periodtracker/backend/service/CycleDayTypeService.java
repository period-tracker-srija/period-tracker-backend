package com.periodtracker.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.periodtracker.backend.model.*;
import com.periodtracker.backend.repository.*;

import jakarta.transaction.Transactional;

import java.util.List;

@Service 
public class CycleDayTypeService {
    private final CycleDayTypeRepository cycleDayTypeRepository;
    private final DailyLogRepository dailyLogRepository;

    public CycleDayTypeService(CycleDayTypeRepository cycleDayTypeRepository, DailyLogRepository dailyLogRepository) {
        this.cycleDayTypeRepository = cycleDayTypeRepository;
        this.dailyLogRepository = dailyLogRepository;
    }

    public List<CycleDayType> getAllTypes() {
        return cycleDayTypeRepository.findAllByOrderByDisplayOrderAsc();
    }

    public List<CycleDayType> getActiveTypes() {
        return cycleDayTypeRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    public CycleDayType createType(String name, String color) {
        CycleDayType type = new CycleDayType();
        type.setName(name);
        type.setColor(color);
        type.setDefault(false);
        type.setActive(true);
        type.setDisplayOrder(nextDisplayOrder());
        return cycleDayTypeRepository.save(type);   // save returns what it saved
    }

    public CycleDayType updateType(Long id, String name, String color) {
        CycleDayType type = cycleDayTypeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cycle day type not found"));
        
        if (name != null && !name.isBlank()) {
            type.setName(name.trim());
        }
        if (color != null && !color.isBlank()) {
            type.setColor(color);
        }

        return cycleDayTypeRepository.save(type);
    }

    public CycleDayType setActive(Long id, boolean active) {
        CycleDayType type = cycleDayTypeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cycle day type not found"));
        type.setActive(active);
        return cycleDayTypeRepository.save(type);
    }

    @Transactional
    public void deleteType(Long id) {
        if (cycleDayTypeRepository.count() <= 1) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "At least one cycle day type must exist"
            );
        }

        CycleDayType type = cycleDayTypeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cycle day type not found"));
        
        List<DailyLog> logsUsingType = dailyLogRepository.findByCycleDayTypeId(id);   // find all logs that have this cycle type and make the cycle type null
        for (DailyLog log : logsUsingType) {
            log.setCycleDayType(null);
            dailyLogRepository.save(log);
        }

        cycleDayTypeRepository.delete(type);
    }

    public void reorderTypes(List<Long> orderedIds) {
        for(int i = 0; i < orderedIds.size(); i++) {
            CycleDayType type = cycleDayTypeRepository.findById(orderedIds.get(i))
                .orElseThrow(() -> new RuntimeException("Cycle day type not found"));
            type.setDisplayOrder(i);
            cycleDayTypeRepository.save(type);
        }
    }

    private int nextDisplayOrder() {
        return cycleDayTypeRepository.findAll().stream()
            .mapToInt(t -> t.getDisplayOrder() == null ? -1 : t.getDisplayOrder())
            .max()
            .orElse(-1) + 1;
    }
}
