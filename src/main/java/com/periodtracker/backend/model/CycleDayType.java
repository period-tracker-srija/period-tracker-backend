package com.periodtracker.backend.model;

import jakarta.persistence.*;

@Entity 
@Table(name = "cycle_day_types")
public class CycleDayType {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String color;
    private boolean isDefault;
    private Integer displayOrder;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
