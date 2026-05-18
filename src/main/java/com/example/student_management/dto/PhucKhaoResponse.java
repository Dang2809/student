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
    private Double currentScore;
    private String lyDo;
    private String status;
    private String phanHoi;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String feedbackSummary;

    // =========================================
    // ✅ Constructor dùng cho JPQL (SELECT new ...)
    // ⚠️ THỨ TỰ PHẢI GIỐNG QUERY
    // =========================================
    public PhucKhaoResponse(
            Long id,
            Long studentId,
            Long subjectId,
            String studentName,
            String subjectName,
            Integer semester,
            Double currentScore,
            String lyDo,
            String status,
            String phanHoi,
            String feedbackSummary,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.studentName = studentName;
        this.subjectName = subjectName;
        this.semester = semester;
        this.currentScore = currentScore;
        this.lyDo = lyDo;
        this.status = status;
        this.phanHoi = phanHoi;
        this.feedbackSummary = feedbackSummary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // =========================================
    // ✅ Constructor dùng khi map từ entity (optional)
    // =========================================
    public PhucKhaoResponse(PhucKhao pk) {
        this.id = pk.getId();
        this.studentId = pk.getStudent().getId();
        this.studentName = pk.getStudent().getFullName();
        this.subjectId = pk.getSubject().getId();
        this.subjectName = pk.getSubject().getName();
        this.semester = pk.getSemester();
        this.currentScore = pk.getCurrentScore();
        this.lyDo = pk.getLyDo();
        this.status = pk.getStatus().name();
        this.phanHoi = pk.getPhanHoi();
        this.createdAt = pk.getCreatedAt();
        this.updatedAt = pk.getUpdatedAt();

        this.feedbackSummary =
                this.status + " - " + (this.phanHoi == null ? "" : this.phanHoi);
    }

    // =========================================
    // GETTERS & SETTERS
    // =========================================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

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

    public String getFeedbackSummary() { return feedbackSummary; }
    public void setFeedbackSummary(String feedbackSummary) { this.feedbackSummary = feedbackSummary; }
}