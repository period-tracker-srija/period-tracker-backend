package com.periodtracker.backend.service;

import org.springframework.stereotype.Service;

import com.periodtracker.backend.repository.*;

import jakarta.transaction.Transactional;

import com.periodtracker.backend.dto.*;
import com.periodtracker.backend.model.*;

import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service 
public class DailyLogService {
    private final DailyLogRepository dailyLogRepository;
    private final CycleDayTypeRepository cycleDayTypeRepository;
    private final SymptomTypeRepository symptomTypeRepository;
    private final SymptomEntryRepository symptomEntryRepository;
    private final ObjectMapper objectMapper;

    public DailyLogService(DailyLogRepository dailyLogRepository, CycleDayTypeRepository cycleDayTypeRepository, SymptomTypeRepository symptomTypeRepository, SymptomEntryRepository symptomEntryRepository, ObjectMapper objectMapper) {
        this.dailyLogRepository = dailyLogRepository;
        this.cycleDayTypeRepository = cycleDayTypeRepository;
        this.symptomTypeRepository = symptomTypeRepository;
        this.symptomEntryRepository = symptomEntryRepository;
        this.objectMapper = objectMapper;
    }

    public List<DailyLog> getAllLogs() {
        return dailyLogRepository.findAll();
    }

    public DailyLogResponse getFullLog(LocalDate logDate) {
        DailyLog log = dailyLogRepository.findByLogDate(logDate).orElse(null);
        
        if(log == null) {
            return new DailyLogResponse(logDate, null, List.of());
        }

        List<SymptomEntryResponse> symptoms = symptomEntryRepository.findByDailyLogId(log.getId()).stream()
            .map(entry -> new SymptomEntryResponse(
                entry.getSymptomType().getId(),
                entry.getSymptomType().getName(),
                entry.getValue()))
            .collect(Collectors.toList());
        
        return new DailyLogResponse(log.getLogDate(), log.getCycleDayType(), symptoms);
    }

    @Transactional 
    public DailyLogResponse saveFullLog(DailyLogRequest request) {
        DailyLog log = dailyLogRepository.findByLogDate(request.getLogDate())
            .orElseGet(DailyLog::new);
        log.setLogDate(request.getLogDate());

        if (request.getCycleDayTypeId() != null) {
            CycleDayType type = cycleDayTypeRepository.findById(request.getCycleDayTypeId())
                .orElseThrow(() -> new RuntimeException("Cycle day type not found"));
            log.setCycleDayType(type);
        } else {
            log.setCycleDayType(null);
        }

        DailyLog savedLog = dailyLogRepository.save(log);

        symptomEntryRepository.deleteByDailyLogId(savedLog.getId());

        if (request.getSymptoms() != null) {
            for (SymptomEntryRequest symptomRequest : request.getSymptoms()) {
                SymptomType type = symptomTypeRepository.findById(symptomRequest.getSymptomTypeId())
                    .orElseThrow(() -> new RuntimeException("Symptom type not found"));

                SymptomEntry entry = new SymptomEntry();
                entry.setDailyLog(savedLog);
                entry.setSymptomType(type);
                entry.setValue(toJson(symptomRequest.getValue()));
                symptomEntryRepository.save(entry);
            }
        }

        return getFullLog(savedLog.getLogDate());
    }

    @Transactional
    public void deleteLog(LocalDate logDate) {
        DailyLog log = dailyLogRepository.findByLogDate(logDate).orElse(null);
        if (log == null) {
            return;
        }
        symptomEntryRepository.deleteByDailyLogId(log.getId());
        dailyLogRepository.deleteByLogDate(logDate);
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException("Could not process symptom value", e);
        }
    }
}
