package com.Lash.StudentManagementSystem.controller;

import com.Lash.StudentManagementSystem.dto.StudentRegistrationRequest;
import com.Lash.StudentManagementSystem.entities.Student;
import com.Lash.StudentManagementSystem.exception.ResourceNotFoundException;
import com.Lash.StudentManagementSystem.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody StudentRegistrationRequest request){
        try{
            Student student = studentService.registerStudent(request);
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        }catch(ResourceNotFoundException exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId){
        try{
            Student student = studentService.getStudentById(studentId);
            return new ResponseEntity<>(student, HttpStatus.OK);
        }catch(ResourceNotFoundException exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> studentList = studentService.getAllStudent();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

}
