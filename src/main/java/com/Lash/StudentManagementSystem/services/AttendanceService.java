package com.Lash.StudentManagementSystem.services;

import com.Lash.StudentManagementSystem.entities.*;
import com.Lash.StudentManagementSystem.exception.BusinessRuleViolationException;
import com.Lash.StudentManagementSystem.exception.ResourceNotFoundException;
import com.Lash.StudentManagementSystem.repositories.AttendanceRecordRepository;
import com.Lash.StudentManagementSystem.repositories.ClassSessionRepository;
import com.Lash.StudentManagementSystem.repositories.FacultyRepository;
import com.Lash.StudentManagementSystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


// Business Logic for Attendance related stuff
@Service
public class AttendanceService {

//  Dependencies of AttendanceService
    private final ClassSessionRepository classSessionRepo;
    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepo;
    private final AttendanceRecordRepository attendanceRepo;


//    Setting up the dependencies using constructor injection
    @Autowired
    public AttendanceService(
        ClassSessionRepository classSessionRepo, StudentRepository studentRepo,
        FacultyRepository facultyRepo, AttendanceRecordRepository attendanceRepo
    ){
        this.classSessionRepo = classSessionRepo;
        this.studentRepo = studentRepo;
        this.facultyRepo = facultyRepo;
        this.attendanceRepo = attendanceRepo;
    }

//    Method for validating the session window.
    private boolean validateSessionWindow(ClassSession session){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startSession = session.getSessionDate().atTime(session.getStartTime());
        LocalDateTime endSession = session.getSessionDate().atTime(session.getEndTime());

        return now.isBefore(endSession) && now.isAfter(startSession);
    }

//    Method for validating time period for marking attendance late
    private boolean validateLateAttendanceWindow(ClassSession session){
        LocalDate today = LocalDate.now();
        LocalDate sessionDate = session.getSessionDate();
        LocalDate validDate = sessionDate.plusDays(14);
        boolean validDay = (today.getDayOfWeek().equals(DayOfWeek.FRIDAY) || today.getDayOfWeek().equals(DayOfWeek.SATURDAY));

        boolean withinRange = today.isEqual(sessionDate)
                                || (today.isAfter(sessionDate) && today.isBefore(validDate) )
                                || today.isEqual(validDate);

        return withinRange && validDay;
    }

    public AttendanceRecord markAttendance(Long sessionId, Long studentId, AttendanceStatus status, Long markedByFacultyId){

        ClassSession session = classSessionRepo.findById(sessionId).orElseThrow(
                ()->new ResourceNotFoundException("Class Session is not found."));

        Faculty faculty = facultyRepo.findById(markedByFacultyId).orElseThrow(
                ()->new ResourceNotFoundException("Faculty not found"));

        Student student = studentRepo.findById(studentId).orElseThrow(
                ()-> new ResourceNotFoundException("Student not found"));

        if(!(validateSessionWindow(session) || validateLateAttendanceWindow(session))){
            throw new BusinessRuleViolationException("Attendance can't mark outside the valid time periods");
        }
        if(attendanceRepo.findByStudentAndSession(student, session).isPresent()){
            throw new BusinessRuleViolationException("Can't save duplicate records for same session and same student");
        }

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setMarkedBy(faculty);
        attendanceRecord.setMarkedAt(LocalDateTime.now());
        attendanceRecord.setStudent(student);
        attendanceRecord.setSession(session);
        attendanceRecord.setStatus(status);
        return attendanceRepo.save(attendanceRecord);

    }


    public AttendanceRecord updateAttendance(Long attendanceRecordId, AttendanceStatus newStatus, Long markedByFacultyId){

        AttendanceRecord attendanceRecord = attendanceRepo.findById(attendanceRecordId).
                                            orElseThrow(() -> new ResourceNotFoundException("No such attendance record found"));

        ClassSession session = attendanceRecord.getSession();

        Faculty faculty = facultyRepo.findById(markedByFacultyId).
                            orElseThrow(() -> new ResourceNotFoundException("No such faculty exists"));

        if(!(validateSessionWindow(session) || validateLateAttendanceWindow(session))){
            throw new BusinessRuleViolationException("Attendance can't mark outside the valid time period");
        }

        attendanceRecord.setStatus(newStatus);
        attendanceRecord.setMarkedBy(faculty);
        attendanceRecord.setMarkedAt(LocalDateTime.now());
        return attendanceRepo.save(attendanceRecord);
    }


    public List<AttendanceRecord> getAttendanceForSession(Long sessionId){
        ClassSession session = classSessionRepo.findById(sessionId).orElseThrow(
                ()->new ResourceNotFoundException("Class Session is not found."));
        return attendanceRepo.findBySession(session);
    }


    public List<AttendanceRecord> getAttendanceForStudent(Long studentId){
        Student student = studentRepo.findById(studentId).orElseThrow(
                ()-> new ResourceNotFoundException("Student not found"));
        return attendanceRepo.findByStudent(student);
    }

}
