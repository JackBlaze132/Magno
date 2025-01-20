package com.unibegue.magno.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "investigation_groups")
public class InvestigationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection(targetClass = LineOfResearch.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "investigation_groups_lines_of_research")
    @Column(name = "line_of_research")
    private List<LineOfResearch> linesOfResearch;
}
