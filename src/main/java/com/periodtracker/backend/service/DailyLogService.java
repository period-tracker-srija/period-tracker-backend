package com.periodtracker.backend.service;

import org.springframework.stereotype.Service;

import com.periodtracker.backend.repository.*;
import com.periodtracker.backend.dto.*;
import com.periodtracker.backend.model.*;

import jakarta.transaction.Transactional;
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

    public List<DailyLogResponse> getAllLogs() {
        return dailyLogRepository.findAll().stream()    // this basically converts DailyLogs into DailyLogResponses.
            .map(log -> getFullLog(log.getLogDate()))   // get the dailylog list and turn it into a stream and map each log to a DailyLogResponse (and add symptoms) (which is what getFullLog does by taking date)
            .collect(Collectors.toList());              // collect and build a list of DailyLogResponses which are sent back
    }

    public DailyLogResponse getFullLog(LocalDate logDate) {     // gets the full log for a date and formats it into a DailyLogResponse
        DailyLog log = dailyLogRepository.findByLogDate(logDate).orElse(null);
        
        if(log == null) {   // no log yet on this date, so just return date and empty cycle day type and symptoms list
            return new DailyLogResponse(logDate, null, List.of());
        }

        List<SymptomEntryResponse> symptoms = symptomEntryRepository.findByDailyLogId(log.getId()).stream()
            .map(entry -> new SymptomEntryResponse(
                entry.getSymptomType().getId(),
                entry.getSymptomType().getName(),
                fromJson(entry.getValue())))
            .collect(Collectors.toList());
        
        return new DailyLogResponse(log.getLogDate(), log.getCycleDayType(), symptoms);
    }

    @Transactional 
    public DailyLogResponse saveFullLog(DailyLogRequest request) {
        DailyLog log = dailyLogRepository.findByLogDate(request.getLogDate())
            .orElseGet(DailyLog::new);
        log.setLogDate(request.getLogDate());       // always set the date from the request

        if (request.getCycleDayTypeId() != null) {      // find the cycle day type through id, if not set it to null
            CycleDayType type = cycleDayTypeRepository.findById(request.getCycleDayTypeId())
                .orElseThrow(() -> new RuntimeException("Cycle day type not found"));
            log.setCycleDayType(type);
        } else {
            log.setCycleDayType(null);
        }

        DailyLog savedLog = dailyLogRepository.save(log);

        symptomEntryRepository.deleteByDailyLogId(savedLog.getId());    // delete existing symptoms and replace with new if they are sent

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

    private Object fromJson(String value) {
        if(value == null || value.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readValue(value, Object.class);
        } catch (Exception e) {
            return value;
        }
    }
}
