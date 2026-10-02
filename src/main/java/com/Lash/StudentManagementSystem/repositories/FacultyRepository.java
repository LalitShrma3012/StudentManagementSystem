package com.Lash.StudentManagementSystem.repositories;

import com.Lash.StudentManagementSystem.entities.Department;
import com.Lash.StudentManagementSystem.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    long countByDepartmentAndDateOfJoiningBetween(Department department, LocalDate start, LocalDate end);
}
