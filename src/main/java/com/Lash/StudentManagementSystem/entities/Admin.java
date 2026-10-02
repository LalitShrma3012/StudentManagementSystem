package com.Lash.StudentManagementSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User{

    @Column(name = "emp_id", nullable = false, unique = true)
    private String empId;

    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    @Column(name = "joining_date")
    private LocalDate dateOfJoining;
}
