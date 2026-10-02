package com.Lash.StudentManagementSystem.services;


import com.Lash.StudentManagementSystem.entities.*;
import com.Lash.StudentManagementSystem.exception.BusinessRuleViolationException;
import com.Lash.StudentManagementSystem.exception.ResourceNotFoundException;
import com.Lash.StudentManagementSystem.repositories.CourseRepository;
import com.Lash.StudentManagementSystem.repositories.EnrollmentRepository;
import com.Lash.StudentManagementSystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepo;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;

    @Autowired
    public EnrollmentService(StudentRepository studentRepo,
                             CourseRepository courseRepo,
                             EnrollmentRepository enrollmentRep){
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRep;
    }
    public Enrollment enrollStudent(Long studentId, Long courseId){
        Course course = courseRepo.findById(courseId)
                        .orElseThrow(()->new ResourceNotFoundException("Course not found"));
        Student student = studentRepo.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student not found")
        );

        boolean isActive = enrollmentRepo.existsByStudentAndCourseAndStatus(student, course, EnrollmentStatus.ACTIVE);
        if(isActive){
            throw new BusinessRuleViolationException(
                    "Student enrollment already exists!");
        }

        int activeCount = enrollmentRepo.countByStudentAndStatus(student, EnrollmentStatus.ACTIVE);
        if(activeCount==6){
            throw new BusinessRuleViolationException(
                    "Single student can't enroll in more than 6 active courses");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setEnrollmentDate(LocalDate.now());

        return enrollmentRepo.save(enrollment);
    }

    public Student dropEnrollment(Long enrollmentId){
        return null;
    }


}