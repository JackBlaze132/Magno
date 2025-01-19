package com.unibegue.magno.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "academic_periods")
public class AcademicPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_current", nullable = false)
    boolean isCurrent;

    @OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentProfile> studentProfiles;

    @OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FunctionaryProfile> functionaryProfiles;

    @OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ResearchSeedbedProfile> researchSeedbedsProfiles;

    @OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExternalUserProfile> externalUserProfiles;
}
