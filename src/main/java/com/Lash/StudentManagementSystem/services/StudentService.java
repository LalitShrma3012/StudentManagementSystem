package com.Lash.StudentManagementSystem.services;

import com.Lash.StudentManagementSystem.dto.StudentRegistrationRequest;
import com.Lash.StudentManagementSystem.entities.*;
import com.Lash.StudentManagementSystem.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepo;
    private final DepartmentRepository departmentRepo;
    private final SectionRepository sectionRepo;

    @Autowired
    public StudentService(StudentRepository studentRepo, DepartmentRepository departmentRepo, SectionRepository sectionRepo) {
        this.studentRepo = studentRepo;
        this.departmentRepo = departmentRepo;
        this.sectionRepo = sectionRepo;
    }

    public Student registerStudent(StudentRegistrationRequest request) {

        Department department = departmentRepo.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Section section = sectionRepo.findById(request.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found"));

        String rollNumber = generateRollNumber(department, section.getBatchYear());

        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword()); // TODO: hash with BCrypt during auth phase
        student.setPhoneNumber(request.getPhoneNumber());
        student.setDob(request.getDob());
        student.setRole(Role.STUDENT);
        student.setDepartment(department);
        student.setSection(section);
        student.setCurrentSemester(request.getCurrentSemester());
        student.setFatherName(request.getFatherName());
        student.setRollNumber(rollNumber);

        return studentRepo.save(student);
    }

    private String generateRollNumber(Department department, Integer batchYear) {
        long existingCount = studentRepo.countByDepartmentAndSection_BatchYear(department, batchYear);
        long nextSequence = existingCount + 1;
        String paddedSequence = String.format("%03d", nextSequence);
        return department.getDepartmentCode() + batchYear + paddedSequence;
    }
}