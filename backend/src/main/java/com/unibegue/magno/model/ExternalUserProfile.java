package com.unibegue.magno.model;

import jakarta.persistence.*;

@Entity
@Table(name = "external_users_profiles")
public class ExternalUserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "external_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    private AcademicPeriod academicPeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_external_user", nullable = false)
    private TypeOfExternalUser typeOfExternalUser;
}
