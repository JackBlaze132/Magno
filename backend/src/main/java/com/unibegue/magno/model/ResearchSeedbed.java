package com.unibegue.magno.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "research_seedbeds")
public class ResearchSeedbed {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob //It means that it will be a large string
    private String mission;

    @Lob
    private String vission;

    @Lob
    private String researchProposalDescription;

    @Column(name = "creation_date", nullable = false)
    LocalDate creationDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "line_of_research", nullable = false)
    LineOfResearch lineOfResearch;

    @OneToMany(mappedBy = "researchSeedbed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResearchSeedbedProfile> researchSeedbedProfiles;
}
