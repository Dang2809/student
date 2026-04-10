package com.example.student_management.dto;

public class PhucKhaoRequest {
    private Long studentId;
    private Long subjectId;
    private Integer semester;
    private Double currentScore;   // thêm điểm hiện tại
    private String lyDo;

    // Getters & Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public Double getCurrentScore() { return currentScore; }
    public void setCurrentScore(Double currentScore) { this.currentScore = currentScore; }

    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }
}
