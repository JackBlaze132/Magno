package com.unibague.magno.domain.model;

import java.util.Set;

public class InvestigationGroup {

    private Long id;
    private String name;
    private Set<LineOfResearch> linesOfResearch;

    public InvestigationGroup(Long id, String name, Set<LineOfResearch> linesOfResearch) {
        this.id = id;
        this.name = name;
        this.linesOfResearch = linesOfResearch;
    }

    public InvestigationGroup() {
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

    public Set<LineOfResearch> getLinesOfResearch() {
        return linesOfResearch;
    }

    public void setLinesOfResearch(Set<LineOfResearch> linesOfResearch) {
        this.linesOfResearch = linesOfResearch;
    }
}
