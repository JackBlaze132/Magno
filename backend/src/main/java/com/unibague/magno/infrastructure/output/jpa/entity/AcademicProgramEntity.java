package com.unibague.magno.infrastructure.output.jpa.entity;

import com.unibague.magno.domain.model.enums.AcademicProgramType;
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
@Table(name = "academic_programs")
public class AcademicProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "program_code", nullable = false, unique = true)
    private String programCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_program_type", nullable = false)
    AcademicProgramType type;

    @ManyToMany(mappedBy = "academicPrograms")
    private Set<StudentProfileEntity> studentProfiles;
}
