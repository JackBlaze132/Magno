package com.unibague.magno.infrastructure.output.jpa.entity;

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
@Table(name = "student_profiles")
public class StudentProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "semester", nullable = false)
    private byte semester;

    @OneToMany(mappedBy = "studentProfile", cascade = CascadeType.REFRESH)
    private Set<ResearchSeedbedStudentProfileEntity> researchSeedbedStudentProfiles;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    private AcademicPeriodEntity academicPeriod;

    @ManyToMany
    @JoinTable(
            name = "student_profiles_academic_programs",
            joinColumns = @JoinColumn(name = "student_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "academic_program_id")
    )
    private Set<AcademicProgramEntity> academicPrograms;

}
