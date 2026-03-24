package com.example.student_management.dto;

public class DeleteResponse {
    private String message;
    private Long id;

    public DeleteResponse(String message, Long id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() { return message; }
    public Long getId() { return id; }
}
