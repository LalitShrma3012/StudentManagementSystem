package com.Lash.StudentManagementSystem.repositories;

import com.Lash.StudentManagementSystem.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
