package com.unibegue.magno.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "investigation_groups_profiles")
public class InvestigationGroupProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "investigation_group_id")
    private InvestigationGroup investigationGroup;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coordinator_id", nullable = false ,unique = true)
    private FunctionaryProfile coordinator;

    @ManyToOne
    @JoinColumn(name = "academic_period_id")
    private AcademicPeriod academicPeriod;

    @JsonIgnore
    @OneToMany(mappedBy = "investigationGroupProfile", cascade = CascadeType.ALL)
    private List<ResearchSeedbedProfile> researchSeedbedsProfiles;
}
