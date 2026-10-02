package com.Lash.StudentManagementSystem.repositories;

import com.Lash.StudentManagementSystem.entities.Department;
import com.Lash.StudentManagementSystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    long countByDepartmentAndSection_BatchYear(Department department, Integer batchYear);
}
