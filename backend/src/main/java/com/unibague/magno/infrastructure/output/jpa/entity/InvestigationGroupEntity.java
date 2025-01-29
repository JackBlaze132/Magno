package com.unibague.magno.infrastructure.output.jpa.entity;

import com.unibague.magno.domain.model.enums.LineOfResearch;
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
@Table(name = "investigation_groups")
public class InvestigationGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection(targetClass = LineOfResearch.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "investigation_groups_lines_of_research",
            joinColumns = @JoinColumn(name = "investigation_group_id")
    )
    @Column(name = "line_of_research")
    private Set<LineOfResearch> linesOfResearch;
}
