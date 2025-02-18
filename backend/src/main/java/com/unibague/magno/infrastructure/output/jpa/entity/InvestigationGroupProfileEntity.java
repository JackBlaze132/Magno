package com.unibague.magno.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "investigation_group_profiles")
public class InvestigationGroupProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "investigation_group_id", nullable = false)
    private InvestigationGroupEntity investigationGroup;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "coordinator_id", nullable = false ,unique = true)
    private FunctionaryProfileEntity coordinator;

    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    private AcademicPeriodEntity academicPeriod;

    @OneToMany(mappedBy = "investigationGroupProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResearchSeedbedProfileEntity> researchSeedbeds;

}
