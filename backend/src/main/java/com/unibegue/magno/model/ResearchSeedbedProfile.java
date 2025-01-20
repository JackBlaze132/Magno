package com.unibegue.magno.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "research_seedbed_profiles")
public class ResearchSeedbedProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "research_seedbed_id", nullable = false)
    private ResearchSeedbed researchSeedbed;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coordinator_id", nullable = false)
    private FunctionaryProfile coordinator;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tutor_id", nullable = true)
    private FunctionaryProfile tutor;

    @ManyToOne
    @JoinColumn(name = "investigation_group_profile_id", nullable = false)
    private InvestigationGroupProfile investigationGroupProfile;

    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    private AcademicPeriod academicPeriod;

    @ManyToMany
    @JoinTable(
            name = "research_seedbeds_profiles_functionary_profiles",
            joinColumns = @JoinColumn(name = "research_seedbed_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_profile_id")
    )
    private List<FunctionaryProfile> functionaryProfiles;

    @OneToMany(mappedBy = "researchSeedbedProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResearchSeedbedStudentProfile> studentProfiles;

    @OneToMany(mappedBy = "researchSeedbedProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExternalUserProfile> externalUserProfiles;

    @Column(name = "was_active", nullable = false)
    private boolean wasActive;
}
