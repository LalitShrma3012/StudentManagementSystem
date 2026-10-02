package com.Lash.StudentManagementSystem.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private Integer credits;

    @Column(name = "semester_number", nullable = false)
    private Integer semesterNumber;
}
