package com.unibague.magno.domain.model.excel.metadata;

public class InvestigationGroupYRMetadata {

    private final String academicPeriodName1;
    private final String academicPeriodName2;

    public InvestigationGroupYRMetadata(String academicPeriodName1, String academicPeriodName2) {
        this.academicPeriodName1 = academicPeriodName1;
        this.academicPeriodName2 = academicPeriodName2;
    }

    public String getAcademicPeriodName1() {
        return academicPeriodName1;
    }

    public String getAcademicPeriodName2() {
        return academicPeriodName2;
    }
}
