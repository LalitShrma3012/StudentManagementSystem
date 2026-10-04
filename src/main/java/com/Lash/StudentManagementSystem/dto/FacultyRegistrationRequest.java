package com.Lash.StudentManagementSystem.dto;

import com.Lash.StudentManagementSystem.entities.Designation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FacultyRegistrationRequest {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dob;
    private Long departmentId;
    private Designation designation;
}

// Class contains the data a faculty which he will enter at the frontend and this class object wraps up the detail.