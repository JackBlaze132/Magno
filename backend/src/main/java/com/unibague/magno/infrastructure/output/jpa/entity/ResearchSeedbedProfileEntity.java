package com.unibague.magno.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "research_seedbeds_profiles")
public class ResearchSeedbedProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "research_seedbed_id")
    private ResearchSeedbedEntity researchSeedbed;

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    private FunctionaryProfileEntity coordinator;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private FunctionaryProfileEntity tutor;

    @ManyToOne
    @JoinColumn(name = "investigation_group_profile_id")
    private InvestigationGroupProfileEntity investigationGroupProfile;

    @ManyToOne
    @JoinColumn(name = "academic_period_id")
    private AcademicPeriodEntity academicPeriod;

    @Column(name = "was_active")
    private Boolean wasActive;
}
