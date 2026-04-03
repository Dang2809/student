package com.example.student_management.model;

import jakarta.persistence.*;


@Entity
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;     // ID sinh viên
    private Double processScore;// điểm quá trình
    private Double examScore;   // điểm thi
    private Double finalScore;  // điểm tổng kết

    // Hàm tính điểm tổng kết
    public void calculateFinal() {
        double rawScore = processScore * subject.getRatioProcess()
                + examScore * subject.getRatioExam();
        this.finalScore = Math.round(rawScore * 10.0) / 10.0;
    }

    // Quan hệ ngược lại: nhiều điểm thuộc về một môn học
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


    // Getter & Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public Double getProcessScore() {
        return processScore;
    }
    public void setProcessScore(Double processScore) {
        this.processScore = processScore;
    }

    public Double getExamScore() {
        return examScore;
    }
    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getFinalScore() {
        return finalScore;
    }
    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }
}
