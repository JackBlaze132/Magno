package com.unibague.magno.domain.model.excel.metadata;

public class InvestigationGroupHYRMetadata {

    private final String academicPeriodName;

    public InvestigationGroupHYRMetadata(String academicPeriodName) {
        this.academicPeriodName = academicPeriodName;
    }

    public String getAcademicPeriodName() {
        return academicPeriodName;
    }
}
