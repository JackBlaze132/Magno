package com.unibague.magno.infrastructure.output.jpa.entity;

import com.unibague.magno.domain.model.enums.LineOfResearch;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * JPA entity representing the {@code investigation_groups} table.
 * Stores research groups with their associated lines of research.
 */
@Getter
@Setter
@Entity
@Builder
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

    @OneToMany(mappedBy = "investigationGroup")
    private Set<InvestigationGroupProfileEntity> investigationGroupProfiles;
}
