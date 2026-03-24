package com.example.student_management.dto;

import java.util.List;

public class StudentListResponse {
    private String message;
    private List<StudentResponse> data;

    public StudentListResponse(String message, List<StudentResponse> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() { return message; }
    public List<StudentResponse> getData() { return data; }
}
