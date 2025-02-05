package com.unibague.magno.infrastructure.output.jpa.entity;

import com.unibague.magno.domain.model.enums.LineOfResearch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "research_seedbeds")
public class ResearchSeedbedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "mission", nullable = false)
    private String mission;

    @Lob
    @Column(name = "vision", nullable = false)
    private String vision;

    @Lob
    @Column(name = "research_proposal_description", nullable = false)
    private String researchProposalDescription;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "line_of_research", nullable = false)
    private LineOfResearch lineOfResearch;
}
