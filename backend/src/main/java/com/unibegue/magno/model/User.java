package com.unibegue.magno.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "identification_number", nullable = false)
    private String identificationNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_code", nullable = true)
    private String userCode;

    @Column(name = "is_external_user", nullable = false)
    private boolean isExternalUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentProfile> studentProfiles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExternalUserProfile> externalUserProfiles;

    @ManyToMany(mappedBy = "users")
    private List<Role> roles;

}
