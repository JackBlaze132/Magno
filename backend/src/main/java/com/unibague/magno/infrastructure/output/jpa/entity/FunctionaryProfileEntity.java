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
@Table(name = "functionary_profiles")
public class FunctionaryProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    private AcademicPeriodEntity academicPeriod;

    @ManyToOne
    @JoinColumn(name = "dependency_id", nullable = false)
    private DependencyEntity dependency;

    @OneToOne(mappedBy = "coordinator", cascade = CascadeType.REFRESH)
    private InvestigationGroupProfileEntity investigationGroup;

    @OneToMany(mappedBy = "coordinator", cascade = CascadeType.REFRESH)
    private Set<ResearchSeedbedProfileEntity> researchSeedbed;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.REFRESH)
    private Set<ResearchSeedbedProfileEntity> researchSeedbeds;

    @ManyToMany
    @JoinTable(
            name = "functionary_profiles_roles",
            joinColumns = @JoinColumn(name = "functionary_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;
}
