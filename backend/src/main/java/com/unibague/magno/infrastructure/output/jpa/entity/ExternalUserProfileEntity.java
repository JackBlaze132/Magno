package com.unibague.magno.infrastructure.output.jpa.entity;

import com.unibague.magno.domain.model.enums.TypeOfExternalUser;
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
@Table(name = "external_user_profiles")
public class ExternalUserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_external_user", nullable = false)
    private TypeOfExternalUser typeOfExternalUser;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    AcademicPeriodEntity academicPeriod;

    @ManyToOne
    @JoinColumn(name = "research_seedbed_profile_id", nullable = false)
    ResearchSeedbedProfileEntity researchSeedbedProfile;
}
