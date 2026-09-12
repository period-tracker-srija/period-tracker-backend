package com.periodtracker.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.periodtracker.backend.repository.*;
import com.periodtracker.backend.model.*;

import java.util.List;

@Component 
public class DataSeeder implements CommandLineRunner {
    private final CycleDayTypeRepository cycleDayTypeRepository;
    private final SymptomTypeRepository symptomTypeRepository;
    private final SymptomOptionRepository symptomOptionRepository;

    public DataSeeder(CycleDayTypeRepository cycleDayTypeRepository, SymptomTypeRepository symptomTypeRepository, SymptomOptionRepository symptomOptionRepository) {
        this.cycleDayTypeRepository = cycleDayTypeRepository;
        this.symptomTypeRepository = symptomTypeRepository;
        this.symptomOptionRepository = symptomOptionRepository;
    }

    @Override 
    public void run(String... args) {
        if (cycleDayTypeRepository.count() == 0) {
            addCycleDayType("Period", "#ff8fa3", 0);
            addCycleDayType("Ovulation", "#6fa8dc", 1);
            addCycleDayType("PMS", "#f4c2c2", 2);
        }

        if (symptomTypeRepository.count() == 0) {
            addScaleSymptom("Bleeding Level", 1, 5, 0);
            addScaleSymptom("Pain/Cramps", 1, 5, 1);
            addChoiceSymptom("Discharge Type", InputType.SINGLE_CHOICE,
                List.of("Dry", "Sticky", "Creamy", "Watery", "Egg White", "Spotting"), 2);
            addChoiceSymptom("Mood Changes", InputType.MULTI_CHOICE,
                List.of("Happy", "Irritable", "Anxious", "Sad", "Energetic", "Calm"), 3);
            addScaleSymptom("Sleep Quality", 1, 5, 4);
            addScaleSymptom("Energy Levels", 1, 5, 5);
            addChoiceSymptom("Cravings", InputType.MULTI_CHOICE,
                List.of("Chocolate", "Salty", "Sweet", "Carbs", "Fatty Foods"), 6);
            addTextSymptom("Notes", 7);
            addBooleanSymptom("Spotting", 8);
        }

        boolean spottingExists = symptomTypeRepository.findAll().stream()
            .anyMatch(t -> "Spotting".equalsIgnoreCase(t.getName()));
        
        if(!spottingExists) {
            int order = symptomTypeRepository.findAll().stream()
                .mapToInt(t -> t.getDisplayOrder() == null ? -1 : t.getDisplayOrder())
                .max()
                .orElse(-1) + 1;
            addBooleanSymptom("Spotting", order);
        }
    }

    private void addCycleDayType(String name, String color, int order) {
        CycleDayType type = new CycleDayType();
        type.setName(name);
        type.setColor(color);
        type.setDefault(true);
        type.setActive(true);
        type.setDisplayOrder(order);
        cycleDayTypeRepository.save(type);
    }

    private void addBooleanSymptom(String name, int order) {
        SymptomType type = new SymptomType();
        type.setName(name);
        type.setInputType(InputType.BOOLEAN);
        type.setDefault(true);
        type.setActive(true);
        type.setDisplayOrder(order);
        symptomTypeRepository.save(type);
    }

    private void addScaleSymptom(String name, int min, int max, int order) {
        SymptomType type = new SymptomType();
        type.setName(name);
        type.setInputType(InputType.SCALE);
        type.setMinValue(min);
        type.setMaxValue(max);
        type.setDefault(true);
        type.setActive(true);
        type.setDisplayOrder(order);
        symptomTypeRepository.save(type);
    }

    private void addTextSymptom(String name, int order) {
        SymptomType type = new SymptomType();
        type.setName(name);
        type.setInputType(InputType.FREE_TEXT);
        type.setDefault(true);
        type.setActive(true);
        type.setDisplayOrder(order);
        symptomTypeRepository.save(type);
    }

    private void addChoiceSymptom(String name, InputType inputType, List<String> optionLabels, int order) {
        SymptomType type = new SymptomType();
        type.setName(name);
        type.setInputType(inputType);
        type.setDefault(true);
        type.setActive(true);
        type.setDisplayOrder(order);
        SymptomType saved = symptomTypeRepository.save(type);

        for (String label : optionLabels) {
            SymptomOption option = new SymptomOption();
            option.setSymptomType(saved);
            option.setLabel(label);
            symptomOptionRepository.save(option);
        }
    }
}
