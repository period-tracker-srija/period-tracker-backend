package com.periodtracker.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.periodtracker.backend.service.*;
import com.periodtracker.backend.dto.*;
import com.periodtracker.backend.model.*;

import java.util.List;

@RestController 
@RequestMapping("/api/cycle-day-types")
public class CycleDayTypeController {
    private final CycleDayTypeService cycleDayTypeService;

    public CycleDayTypeController(CycleDayTypeService cycleDayTypeService) {
        this.cycleDayTypeService = cycleDayTypeService;
    }

    @GetMapping("/all")
    public List<CycleDayType> getAllTypes() {
        return cycleDayTypeService.getAllTypes();
    }

    @GetMapping
    public List<CycleDayType> getActiveTypes() {
        return cycleDayTypeService.getActiveTypes();
    }

    @PostMapping 
    public CycleDayType createType(@RequestBody CycleDayTypeRequest request) {
        return cycleDayTypeService.createType(request.getName(), request.getColor());
    }

    @PutMapping("/{id}")
    public CycleDayType updateType(@PathVariable Long id, @RequestBody CycleDayTypeRequest request) {
        return cycleDayTypeService.updateType(id, request.getName(), request.getColor());
    }

    @PutMapping("/{id}/active")
    public CycleDayType setActive(@PathVariable Long id, @RequestBody ActiveRequest request) {
        return cycleDayTypeService.setActive(id, request.isActive());
    }

    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable Long id) {
        cycleDayTypeService.deleteType(id);
    }

    @PutMapping("/reorder")
    public void reorderTypes(@RequestBody ReorderRequest request) {
        cycleDayTypeService.reorderTypes(request.getOrderedIds());
    }
}
