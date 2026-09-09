package com.periodtracker.backend.service;

import org.springframework.stereotype.Service;

import com.periodtracker.backend.model.CycleDayType;
import com.periodtracker.backend.repository.CycleDayTypeRepository;

import java.util.List;

@Service 
public class CycleDayTypeService {
    private final CycleDayTypeRepository cycleDayTypeRepository;

    public CycleDayTypeService(CycleDayTypeRepository cycleDayTypeRepository) {
        this.cycleDayTypeRepository = cycleDayTypeRepository;
    }

    public List<CycleDayType> getAllTypes() {
        return cycleDayTypeRepository.findAllByOrderByDisplayOrderAsc();
    }

    public CycleDayType createType(String name, String color) {
        CycleDayType type = new CycleDayType();
        type.setName(name);
        type.setColor(color);
        type.setDefault(false);
        type.setDisplayOrder(nextDisplayOrder());
        return cycleDayTypeRepository.save(type);
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
