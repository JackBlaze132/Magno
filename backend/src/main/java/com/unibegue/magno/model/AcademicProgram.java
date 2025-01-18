package com.unibegue.magno.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class AcademicProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "program_code", nullable = false, unique = true)
    private String programCode;

    @JsonIgnore
    @ManyToMany(mappedBy = "academicPrograms", fetch = FetchType.LAZY)
    private List<StudentProfile> studentProfiles;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_academic_program", nullable = false)
    TypeOfAcademicProgram typeOfAcademicProgram;

}
