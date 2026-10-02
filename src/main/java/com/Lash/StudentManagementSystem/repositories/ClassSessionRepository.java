package com.Lash.StudentManagementSystem.repositories;

import com.Lash.StudentManagementSystem.entities.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {

}
