package com.Lash.StudentManagementSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{

    @Column(name = "roll_number", nullable = false, unique = true)
    private String rollNumber;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "current_semester", nullable = false)
    private Integer currentSemester;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;
}
