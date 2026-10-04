package com.Lash.StudentManagementSystem.services;

import com.Lash.StudentManagementSystem.dto.AdminRegistrationRequest;
import com.Lash.StudentManagementSystem.entities.Admin;
import com.Lash.StudentManagementSystem.entities.Role;
import com.Lash.StudentManagementSystem.repositories.AdminRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class AdminService {

    private final AdminRepository adminRepo;

    public AdminService(AdminRepository adminRepo){
        this.adminRepo = adminRepo;
    }

    public Admin registerAdmin(AdminRegistrationRequest request){
        String empId = generateEmployeeId();
        Admin admin = new Admin();
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setDob(request.getDob());
        admin.setEmpId(empId);
        admin.setPassword(request.getPassword());
        admin.setPhoneNumber(request.getPhoneNumber());
        admin.setAccessLevel(request.getAccessLevel());
        admin.setDateOfJoining(LocalDate.now());
        admin.setRole(Role.ADMIN);
        return adminRepo.save(admin);
    }

    private String generateEmployeeId() {
        int year = LocalDate.now().getYear();
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        long existingSequence = adminRepo.countByDateOfJoiningBetween(start, end);
        return "ADM" + year + String.format("%03d", existingSequence+1);
    }
}