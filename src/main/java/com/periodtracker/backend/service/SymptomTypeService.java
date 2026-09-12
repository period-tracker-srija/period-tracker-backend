package com.periodtracker.backend.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.periodtracker.backend.repository.*;
import com.periodtracker.backend.dto.*;
import com.periodtracker.backend.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Service 
public class SymptomTypeService {
    private final SymptomTypeRepository symptomTypeRepository;
    private final SymptomOptionRepository symptomOptionRepository;
    private final SymptomEntryRepository symptomEntryRepository;

    public SymptomTypeService(SymptomTypeRepository symptomTypeRepository, SymptomOptionRepository symptomOptionRepository, SymptomEntryRepository symptomEntryRepository) {
        this.symptomTypeRepository = symptomTypeRepository;
        this.symptomOptionRepository = symptomOptionRepository;
        this.symptomEntryRepository = symptomEntryRepository;
    }

    public List<SymptomTypeResponse> getActiveSymptomTypes() {
        return symptomTypeRepository.findByActiveTrueOrderByDisplayOrderAsc().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<SymptomTypeResponse> getAllSymptomTypes() {
        return symptomTypeRepository.findAllByOrderByDisplayOrderAsc().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public SymptomTypeResponse setActive(Long id, boolean active) {
        SymptomType type = symptomTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Symptom type not found"));
        type.setActive(active);
        return toResponse(symptomTypeRepository.save(type));
    }

    public SymptomTypeResponse createSymptomType(SymptomTypeRequest request) {
        SymptomType type = new SymptomType();
        type.setName(request.getName());
        type.setInputType(request.getInputType());
        type.setMinValue(request.getMinValue());
        type.setMaxValue(request.getMaxValue());
        type.setDefault(false);
        type.setActive(true);
        type.setDisplayOrder(nextDisplayOrder());
        SymptomType saved = symptomTypeRepository.save(type);

        if (request.getOptions() != null) {
            for (String label : request.getOptions()) {
                SymptomOption option = new SymptomOption();
                option.setSymptomType(saved);
                option.setLabel(label);
                symptomOptionRepository.save(option);
            }
        }

        return toResponse(saved);
    }

    public SymptomTypeResponse updateSymptomType(Long id, SymptomTypeRequest request) {
        SymptomType type = symptomTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Symptom type not found"));

        if(request.getName() != null && !request.getName().isBlank()) {
            type.setName(request.getName().trim());     // trim leading and trailing whitespaces
        }
        if(request.getMinValue() != null) {
            type.setMinValue(request.getMinValue());
        }
        if(request.getMaxValue() != null) {
            type.setMaxValue(request.getMaxValue());
        }

        return toResponse(symptomTypeRepository.save(type));
    }

    @Transactional
    public void deleteSymptomType(Long id) {
        SymptomType type = symptomTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Symptom type not found"));

        symptomEntryRepository.deleteBySymptomTypeId(id);
        symptomOptionRepository.deleteBySymptomTypeId(id);
        symptomTypeRepository.delete(type);
    }

    public void reorderSymptomTypes(List<Long> orderedIds) {
        for (int i = 0; i < orderedIds.size(); i++) {
            SymptomType type = symptomTypeRepository.findById(orderedIds.get(i))
                .orElseThrow(() -> new RuntimeException("Symptom type not found"));
            type.setDisplayOrder(i);
            symptomTypeRepository.save(type);
        }
    }

    private int nextDisplayOrder() {
        return symptomTypeRepository.findAll().stream()
            .mapToInt(t -> t.getDisplayOrder() == null ? -1 : t.getDisplayOrder())
            .max()
            .orElse(-1) + 1;
    }

    private SymptomTypeResponse toResponse(SymptomType type) {
        List<SymptomOptionResponse> options = symptomOptionRepository.findBySymptomTypeId(type.getId()).stream()
            .map(o -> new SymptomOptionResponse(o.getId(), o.getLabel()))
            .collect(Collectors.toList());

        return new SymptomTypeResponse(type.getId(), type.getName(), type.getInputType(),
            type.getMinValue(), type.getMaxValue(), options, type.isActive());
    }
}
