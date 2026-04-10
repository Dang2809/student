package com.example.student_management.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "phuckhao")
public class PhucKhao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;

    @Column(nullable=false)
    private int semester;

    @Column(name="current_score")
    private Double currentScore;   // điểm hiện tại

    @Column(nullable=false)
    private String lyDo;

    private String phanHoi;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // enum trạng thái
    public enum Status { PENDING, APPROVED, REJECTED }

    // ===== Lifecycle Callbacks =====
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ===== Getters & Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public Double getCurrentScore() { return currentScore; }
    public void setCurrentScore(Double currentScore) { this.currentScore = currentScore; }

    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    public String getPhanHoi() { return phanHoi; }
    public void setPhanHoi(String phanHoi) { this.phanHoi = phanHoi; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
