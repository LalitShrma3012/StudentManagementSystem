package com.Lash.StudentManagementSystem.dto;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class StudentRegistrationRequest {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dob;
    private String fatherName;
    private Integer currentSemester;
    private Long departmentId;
    private Long sectionId;
}