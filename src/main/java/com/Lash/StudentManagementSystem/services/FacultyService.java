package com.Lash.StudentManagementSystem.services;

import com.Lash.StudentManagementSystem.dto.FacultyRegistrationRequest;
import com.Lash.StudentManagementSystem.entities.Department;
import com.Lash.StudentManagementSystem.entities.Faculty;
import com.Lash.StudentManagementSystem.entities.Role;
import com.Lash.StudentManagementSystem.repositories.DepartmentRepository;
import com.Lash.StudentManagementSystem.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FacultyService{


    private final FacultyRepository facultyRepo;
    private final DepartmentRepository departmentRepo;

    @Autowired
    public FacultyService(FacultyRepository facultyRepo, DepartmentRepository departmentRepo){
        this.facultyRepo = facultyRepo;
        this.departmentRepo = departmentRepo;
    }


    public Faculty registerFaculty(FacultyRegistrationRequest request){

        Department department = departmentRepo.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Faculty faculty = new Faculty();

        LocalDate joiningDate = LocalDate.now();
        String empId = generateEmployeeId(department, joiningDate.getYear());

        faculty.setEmpId(empId);
        faculty.setFullName(request.getFullName());
        faculty.setEmail(request.getEmail());
        faculty.setPassword(request.getPassword());
        faculty.setPhoneNumber(request.getPhoneNumber());
        faculty.setDob(request.getDob());
        faculty.setDepartment(department);
        faculty.setDesignation(request.getDesignation());
        faculty.setDateOfJoining(joiningDate);
        faculty.setRole(Role.FACULTY);
        return facultyRepo.save(faculty);
    }

    public String generateEmployeeId(Department department, int year){
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        long existingCount = facultyRepo.countByDepartmentAndDateOfJoiningBetween(department, start, end);
        String nextSequence = String.format("%03d", existingCount+1);
        return department.getDepartmentCode() + String.valueOf(year) + nextSequence;
    }
}
