package com.example.student_management.dto;

import com.example.student_management.model.PhucKhao;
import java.time.LocalDateTime;

public class PhucKhaoResponse {
    private Long id;
    private Long studentId;
    private Long subjectId;
    private String studentName;
    private String subjectName;
    private Integer semester;
    private Double currentScore;   // thêm điểm hiện tại
    private String lyDo;
    private String status;
    private String phanHoi;
    private LocalDateTime createdAt;   // thêm
    private LocalDateTime updatedAt;   // thêm

    public PhucKhaoResponse(PhucKhao pk) {
        this.id = pk.getId();
        this.studentId = pk.getStudent().getId();
        this.studentName = pk.getStudent().getFullName();
        this.subjectId = pk.getSubject().getId();
        this.subjectName = pk.getSubject().getName();
        this.semester = pk.getSemester();
        this.currentScore = pk.getCurrentScore(); // lấy từ entity
        this.lyDo = pk.getLyDo();
        this.status = pk.getStatus().name();
        this.phanHoi = pk.getPhanHoi();
        this.createdAt = pk.getCreatedAt();   // lấy từ entity
        this.updatedAt = pk.getUpdatedAt();   // lấy từ entity
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public Double getCurrentScore() { return currentScore; }
    public void setCurrentScore(Double currentScore) { this.currentScore = currentScore; }

    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPhanHoi() { return phanHoi; }
    public void setPhanHoi(String phanHoi) { this.phanHoi = phanHoi; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
