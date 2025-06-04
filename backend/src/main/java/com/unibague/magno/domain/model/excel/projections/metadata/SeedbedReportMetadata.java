package com.unibague.magno.domain.model.excel.projections.metadata;

public class SeedbedReportMetadata {
    private final String researchSeedbedName;
    private final String academicPeriodName;

    public SeedbedReportMetadata(String researchSeedbedName, String academicPeriodName) {
        this.researchSeedbedName = researchSeedbedName;
        this.academicPeriodName = academicPeriodName;
    }

    public String getResearchSeedbedName() {
        return researchSeedbedName;
    }

    public String getAcademicPeriodName() {
        return academicPeriodName;
    }
}
