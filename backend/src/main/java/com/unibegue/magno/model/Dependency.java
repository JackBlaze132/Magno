package com.unibegue.magno.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dependencies")
public class Dependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    //If some Dependency is deleted, the profiles associated won't be deleted
    @JsonIgnore
    @OneToMany(mappedBy = "dependency", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<FunctionaryProfile> functionaryProfiles;
}
