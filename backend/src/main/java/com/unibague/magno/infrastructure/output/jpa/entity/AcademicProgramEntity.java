package com.unibague.magno.infrastructure.output.jpa.entity;

import com.unibague.magno.domain.model.enums.AcademicProgramType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * JPA entity representing the {@code academic_programs} table.
 * Stores academic program information (undergraduate/postgraduate offerings).
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "academic_programs")
@ToString(exclude = "studentProfiles")
public class AcademicProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = false)
    private String name;

    @Column(name = "program_code", nullable = false, unique = true)
    private String programCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_program_type", nullable = false)
    AcademicProgramType type;

    @ManyToMany(mappedBy = "academicPrograms")
    private Set<StudentProfileEntity> studentProfiles;
}
