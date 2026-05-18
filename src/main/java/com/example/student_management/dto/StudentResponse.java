package com.example.student_management.dto;

import java.time.LocalDate;
import com.example.student_management.model.Student;

public class StudentResponse {
    private Long id;
    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String email;
    private String phone;
    private Long userId;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.fullName = student.getFullName();
        this.gender = student.getGender();
        this.dateOfBirth = student.getDateOfBirth();
        this.address = student.getAddress();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.userId = student.getUser() != null ? student.getUser().getId() : null; // thêm để trả về đúng dữ liệu
    }


    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getGender() { return gender; }
    public LocalDate getDateOfBirth() { return dateOfBirth; } // sửa lại kiểu trả về
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Long getUserId() { return userId; }
}
