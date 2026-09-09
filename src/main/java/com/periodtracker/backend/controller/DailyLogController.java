package com.periodtracker.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.periodtracker.backend.service.DailyLogService;
import com.periodtracker.backend.dto.DailyLogRequest;
import com.periodtracker.backend.dto.DailyLogResponse;
import com.periodtracker.backend.model.*;

import java.time.LocalDate;
import java.util.List;

@RestController 
@RequestMapping("/api/daily-logs")
public class DailyLogController {
    private final DailyLogService dailyLogService;

    public DailyLogController(DailyLogService dailyLogService) {
        this.dailyLogService = dailyLogService;
    }

    @GetMapping 
    public List<DailyLog> getAllLogs() {
        return dailyLogService.getAllLogs();
    }

    @GetMapping("/{date}")
    public DailyLogResponse getFullLog(@PathVariable("date") LocalDate date) {
        return dailyLogService.getFullLog(date);
    }

    @PostMapping 
    public DailyLogResponse saveFullLog(@RequestBody DailyLogRequest request) {
        return dailyLogService.saveFullLog(request);
    }

    @DeleteMapping("/{date}")
    public void deleteLog(@PathVariable("date") LocalDate date) {
        dailyLogService.deleteLog(date);
    }
}   
