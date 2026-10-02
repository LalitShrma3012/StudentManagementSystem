package com.Lash.StudentManagementSystem.repositories;

import com.Lash.StudentManagementSystem.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    long countByDateOfJoiningBetween(LocalDate start, LocalDate end);

}
