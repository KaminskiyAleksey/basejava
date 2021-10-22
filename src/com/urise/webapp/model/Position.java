package com.urise.webapp.model;

import java.time.LocalDate;

public class Position {
    private String positionName;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String description;


    public Position(String positionName, LocalDate beginDate, LocalDate endDate, String description) {
        this.positionName = positionName;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getPositionName() {
        return positionName;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }
}
