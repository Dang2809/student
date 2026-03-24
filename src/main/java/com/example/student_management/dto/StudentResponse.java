package com.example.student_management.dto;

import java.time.LocalDate;
import com.example.student_management.model.Student;

public class StudentResponse {
    private Long id;
    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;

    public StudentResponse(String message, Student student) {
        this.id = student.getId();
        this.fullName = student.getFullName();
        this.gender = student.getGender();
        this.dateOfBirth = student.getDateOfBirth();
        this.address = student.getAddress();
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getGender() { return gender; }
    public LocalDate getDateOfBirth() { return dateOfBirth; } // sửa lại kiểu trả về
    public String getAddress() { return address; }
}
