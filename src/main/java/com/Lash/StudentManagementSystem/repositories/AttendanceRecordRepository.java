package com.Lash.StudentManagementSystem.repositories;

import com.Lash.StudentManagementSystem.entities.AttendanceRecord;
import com.Lash.StudentManagementSystem.entities.ClassSession;
import com.Lash.StudentManagementSystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

    Optional<AttendanceRecord> findByStudentAndSession(Student student, ClassSession session);

    List<AttendanceRecord> findBySession(ClassSession session);

    List<AttendanceRecord> findByStudent(Student student);
}
