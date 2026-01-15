package com.unibague.magno.domain.model.excel.metadata;

/**
 * Metadata container for active seedbeds Excel reports.
 */
public class ActiveSeedbedsMetadata {

    private final String academicPeriodName;

    public ActiveSeedbedsMetadata(String academicPeriodName) {
        this.academicPeriodName = academicPeriodName;
    }

    public String getAcademicPeriodName() {
        return academicPeriodName;
    }
}
