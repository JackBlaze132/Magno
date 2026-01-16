package com.unibague.magno.infrastructure.output.jpa.entity;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * JPA entity representing the {@code roles} table.
 * Stores seedbed roles with their descriptions.
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private SeedbedRole name;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "role")
    private Set<StudentProfileEntity> studentProfileEntity;

    @OneToMany(mappedBy = "role")
    private Set<FunctionaryProfileEntity> functionaryProfileEntity;
}
