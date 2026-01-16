package com.unibague.magno.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * JPA entity representing the {@code research_seedbeds_profiles} table.
 * Stores research seedbed configurations for specific academic periods.
 */
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

    @OneToMany(mappedBy = "researchSeedbedProfile", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<ResearchSeedbedStudentProfileEntity> researchSeedbedProfiles;

    @ManyToOne
    @JoinColumn(name = "research_seedbed_id", nullable = false)
    private ResearchSeedbedEntity researchSeedbed;

    @ManyToOne
    @JoinColumn(name = "coordinator_id", nullable = false)
    private FunctionaryProfileEntity coordinator;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = true)
    private FunctionaryProfileEntity tutor;

    @ManyToOne
    @JoinColumn(name = "investigation_group_profile_id", nullable = false)
    private InvestigationGroupProfileEntity investigationGroupProfile;

    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    private AcademicPeriodEntity academicPeriod;

    @Column(name = "was_active", nullable = false)
    private Boolean wasActive;
}
