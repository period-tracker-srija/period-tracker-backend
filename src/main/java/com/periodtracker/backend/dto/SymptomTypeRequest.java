package com.periodtracker.backend.dto;

import java.util.List;

import com.periodtracker.backend.model.InputType;

public class SymptomTypeRequest {
    private String name;
    private InputType inputType;
    private Integer minValue;
    private Integer maxValue;
    private List<String> options;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public InputType getInputType() { return inputType; }
    public void setInputType(InputType inputType) { this.inputType = inputType; }

    public Integer getMinValue() { return minValue; }
    public void setMinValue(Integer minValue) { this.minValue = minValue; }

    public Integer getMaxValue() { return maxValue; }
    public void setMaxValue(Integer maxValue) { this.maxValue = maxValue; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
}
