package com.unibague.magno.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "academic_periods")
public class AcademicPeriodEntity {

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
    private Set<FunctionaryProfileEntity> functionaryProfiles;

    @OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InvestigationGroupProfileEntity> investigationGroupProfiles;

    @OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResearchSeedbedProfileEntity> researchSeedbedProfiles;

    @OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentProfileEntity> studentProfileEntities;
}
