package com.periodtracker.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.periodtracker.backend.service.*;
import com.periodtracker.backend.dto.*;

import java.util.List;

@RestController 
@RequestMapping("/api/symptom-types")
public class SymptomTypeController {
    private final SymptomTypeService symptomTypeService;

    public SymptomTypeController(SymptomTypeService symptomTypeService) {
        this.symptomTypeService = symptomTypeService;
    }

    @GetMapping 
    public List<SymptomTypeResponse> getActiveSymptomTypes() {
        return symptomTypeService.getActiveSymptomTypes();
    }

    @PostMapping 
    public SymptomTypeResponse createSymptomType(@RequestBody SymptomTypeRequest request) {
        return symptomTypeService.createSymptomType(request);
    }

    @DeleteMapping("/{id}")
    public void deactivateSymptomType(@PathVariable("id") Long id) {
        symptomTypeService.deactivateSymptomType(id);
    }

    @PutMapping("/reorder")
    public void reorderSymptomTypes(@RequestBody ReorderRequest request) {
        symptomTypeService.reorderSymptomTypes(request.getOrderedIds());
    }
}
