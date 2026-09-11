package com.periodtracker.backend.model;

import jakarta.persistence.*;

@Entity 
@Table(name = "symptoms_types")
public class SymptomType {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private boolean isDefault;
    private boolean active = true;
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    private InputType inputType;

    private Integer minValue;
    private Integer maxValue;

    // no options field because separate table (similar to cdts in daily log)

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Integer getDisplayOrder() { return displayOrder;}
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }

    public InputType getInputType() { return inputType; }
    public void setInputType(InputType inputType) { this.inputType = inputType; }

    public Integer getMinValue() { return minValue; }
    public void setMinValue(Integer minValue) { this.minValue = minValue; }

    public Integer getMaxValue() { return maxValue; }
    public void setMaxValue(Integer maxValue) { this.maxValue = maxValue; }
}
