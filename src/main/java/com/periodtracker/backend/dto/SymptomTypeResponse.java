package com.periodtracker.backend.dto;

import com.periodtracker.backend.model.InputType;

import java.util.List;

public class SymptomTypeResponse {
    private Long id;
    private String name;
    private InputType inputType;
    private Integer minValue;
    private Integer maxValue;
    private List<SymptomOptionResponse> options;
    private boolean active;

    public SymptomTypeResponse(Long id, String name, InputType inputType, Integer minValue, Integer maxValue, List<SymptomOptionResponse> options, boolean active) {
        this.id = id;
        this.name = name;
        this.inputType = inputType;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.options = options;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public InputType getInputType() { return inputType; }
    public Integer getMinValue() { return minValue; }
    public Integer getMaxValue() { return maxValue; }
    public List<SymptomOptionResponse> getOptions() { return options; }
    public boolean isActive() { return active; }
}
