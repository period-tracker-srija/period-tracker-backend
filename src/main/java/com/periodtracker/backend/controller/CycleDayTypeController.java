package com.periodtracker.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.periodtracker.backend.service.*;
import com.periodtracker.backend.dto.CycleDayTypeRequest;
import com.periodtracker.backend.dto.ReorderRequest;
import com.periodtracker.backend.model.*;

import java.util.List;

@RestController 
@RequestMapping("/api/cycle-day-types")
public class CycleDayTypeController {
    private final CycleDayTypeService cycleDayTypeService;

    public CycleDayTypeController(CycleDayTypeService cycleDayTypeService) {
        this.cycleDayTypeService = cycleDayTypeService;
    }

    @GetMapping
    public List<CycleDayType> getAllTypes() {
        return cycleDayTypeService.getAllTypes();
    }

    @PostMapping 
    public CycleDayType createType(@RequestBody CycleDayTypeRequest request) {
        return cycleDayTypeService.createType(request.getName(), request.getColor());
    }

    @PutMapping("/reorder")
    public void reorderTypes(@RequestBody ReorderRequest request) {
        cycleDayTypeService.reorderTypes(request.getOrderedIds());
    }
}
