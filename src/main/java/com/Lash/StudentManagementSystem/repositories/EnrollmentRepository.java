package com.Lash.StudentManagementSystem.repositories;

import com.Lash.StudentManagementSystem.entities.Course;
import com.Lash.StudentManagementSystem.entities.Enrollment;
import com.Lash.StudentManagementSystem.entities.EnrollmentStatus;
import com.Lash.StudentManagementSystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndCourseAndStatus(Student student, Course course, EnrollmentStatus enrollmentStatus);

    int countByStudentAndStatus(Student student, EnrollmentStatus enrollmentStatus);
}