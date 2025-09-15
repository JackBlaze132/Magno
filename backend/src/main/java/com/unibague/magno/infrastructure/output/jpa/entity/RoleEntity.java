package com.unibague.magno.infrastructure.output.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<StudentProfileEntity> studentProfileEntities;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<FunctionaryProfileEntity> functionaryProfileEntities;
}
