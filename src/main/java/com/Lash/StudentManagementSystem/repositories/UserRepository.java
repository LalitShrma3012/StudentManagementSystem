package com.Lash.StudentManagementSystem.repositories;

import com.Lash.StudentManagementSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}