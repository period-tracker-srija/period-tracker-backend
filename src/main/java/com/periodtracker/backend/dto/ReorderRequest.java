package com.periodtracker.backend.dto;

import java.util.List;

public class ReorderRequest {
    public List<Long> orderedIds;

    public List<Long> getOrderedIds() { return orderedIds; }
    public void setOrderedIds(List<Long> orderedIds) { this.orderedIds = orderedIds; }
}
