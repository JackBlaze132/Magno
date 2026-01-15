package com.unibague.magno.domain.model;

import java.time.LocalDate;

/**
 * Domain model representing an academic period in the academic calendar.
 */
public class AcademicPeriod {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrent;
    private boolean isVisible;

    public AcademicPeriod(Long id, String name, LocalDate startDate, LocalDate endDate, boolean isCurrent, boolean isVisible) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCurrent = isCurrent;
        this.isVisible = isVisible;
    }

    private AcademicPeriod() {
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
