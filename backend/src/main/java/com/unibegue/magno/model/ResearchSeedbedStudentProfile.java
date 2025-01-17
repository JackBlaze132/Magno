package com.unibegue.magno.model;

import jakarta.persistence.*;

@Entity
@Table(name = "research_seedbed_students_profiles")
public class ResearchSeedbedStudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "research_seedbed_profile_id", nullable = false)
    private ResearchSeedbedProfile researchSeedbedProfile;

    @ManyToOne
    @JoinColumn(name = "student_profile_id", nullable = false)
    private StudentProfile studentProfile;

    @Column(name = "wasActive", nullable = false)
    private boolean wasActive;
}

