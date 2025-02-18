package com.unibague.magno.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "research_seedbeds_student_profiles")
public class ResearchSeedbedStudentProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "research_seedbed_profile_id", nullable = false)
    ResearchSeedbedProfileEntity researchSeedbedProfile;

    @ManyToOne
    @JoinColumn(name = "student_profile_id", nullable = false)
    StudentProfileEntity studentProfile;


    @Column(name = "was_active", nullable = false)
    private Boolean wasActive;
}
