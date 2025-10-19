package com.unibague.magno.infrastructure.output.jpa.entity;

import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;
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
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    // Identification number isn't unique because the same person can have multiple roles
    // (e.g., student and functionary) so, the same identification number can be associated with multiple users,
    // the way to distinguish them is by the email.
    @Column(name = "identification_number", nullable = false)
    private String identificationNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_code", nullable = true)
    private String userCode;

    @Column(name = "is_external_user", nullable = false)
    private boolean isExternalUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_internal_user", nullable = true)
    private TypeOfInternalUser typeOfInternalUser;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<FunctionaryProfileEntity> functionaryProfiles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<StudentProfileEntity> studentProfileEntities;
}
