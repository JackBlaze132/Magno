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

    public ResearchSeedbed(Long id, String name, String mission, String vision,String researchProposalDescription,
                           LocalDate creationDate, LineOfResearch lineOfResearch) {
        this.id = id;
        this.name = name;
        this.mission = mission;
        this.vision = vision;
        this.researchProposalDescription = researchProposalDescription;
        this.creationDate = creationDate;
        this.lineOfResearch = lineOfResearch;
    }

    public ResearchSeedbed() {
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

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getResearchProposalDescription() {
        return researchProposalDescription;
    }

    public void setResearchProposalDescription(String researchProposalDescription) {
        this.researchProposalDescription = researchProposalDescription;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LineOfResearch getLineOfResearch() {
        return lineOfResearch;
    }

    public void setLineOfResearch(LineOfResearch lineOfResearch) {
        this.lineOfResearch = lineOfResearch;
    }
}
