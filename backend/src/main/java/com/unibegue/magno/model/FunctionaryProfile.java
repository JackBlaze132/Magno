package com.unibegue.magno.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "functionary_profiles")
public class FunctionaryProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    private AcademicPeriod academicPeriod;

    @ManyToOne
    @JoinColumn(name = "dependency_id")
    private Dependency dependency;

    @OneToOne(mappedBy = "coordinator")
    @JsonIgnore
    private InvestigationGroupProfile investigationGroup;

    @OneToOne(mappedBy = "coordinator")
    @JsonIgnore
    private ResearchSeedbedProfile researchSeedbedCoordinator;

    @OneToOne(mappedBy = "tutor", cascade = CascadeType.ALL)
    @JsonIgnore
    private ResearchSeedbedProfile researchSeedbedTutor;

    @ManyToMany(mappedBy = "functionaryProfiles")
    private List<ResearchSeedbedProfile> researchSeedbeds;
}
