package com.unibague.magno.domain.model.certificates.studentcertificates;

import java.time.LocalDate;

public class StudentSeedbedParticipation {
    private LocalDate startDate;
    private LocalDate endDate;
    private String seedbedCoordinatorName;

    public StudentSeedbedParticipation(LocalDate startDate, LocalDate endDate, String investigationGroupCoordinatorName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.seedbedCoordinatorName = investigationGroupCoordinatorName;
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

    public String getSeedbedCoordinatorName() {
        return seedbedCoordinatorName;
    }

    public void setSeedbedCoordinatorName(String seedbedCoordinatorName) {
        this.seedbedCoordinatorName = seedbedCoordinatorName;
    }
}
