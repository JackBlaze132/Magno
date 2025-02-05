package com.unibague.magno.domain.model;

import com.unibague.magno.domain.model.enums.LineOfResearch;

import java.time.LocalDate;

public class ResearchSeedbed {

    private Long id;
    private String name;
    private String mission;
    private String vision;
    private String researchProposalDescription;
    private LocalDate creationDate;
    private LineOfResearch lineOfResearch;
}
