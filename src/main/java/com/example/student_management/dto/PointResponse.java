package com.example.student_management.dto;

import com.example.student_management.model.Point;

public class PointResponse {
    private Long id;
    private Long studentId;
    private Long subjectId;
    private String subjectName;
    private Integer semester;       // thêm học kỳ
    private Double processScore;
    private Double examScore;
    private Double finalScore;

    public PointResponse(Point p) {
        this.id = p.getId();
        this.studentId = p.getStudentId();
        this.subjectId = p.getSubject().getId();
        this.subjectName = p.getSubject().getName();
        this.semester = p.getSubject().getSemester(); // map từ Subject
        this.processScore = p.getProcessScore();
        this.examScore = p.getExamScore();
        this.finalScore = p.getFinalScore();
    }

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public Double getProcessScore() { return processScore; }
    public void setProcessScore(Double processScore) { this.processScore = processScore; }

    public Double getExamScore() { return examScore; }
    public void setExamScore(Double examScore) { this.examScore = examScore; }

    public Double getFinalScore() { return finalScore; }
    public void setFinalScore(Double finalScore) { this.finalScore = finalScore; }
}
