package com.example.student_management.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;       // tên môn học
    private int credits;       // số tín chỉ
    private int semester;      // học kỳ (số)
    private Double ratioProcess; // tỷ lệ quá trình
    private Double ratioExam;    // tỷ lệ thi

    // Khi xóa Subject thì toàn bộ Point liên quan cũng bị xóa
    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Point> points;

    // Getter & Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Double getRatioProcess() {
        return ratioProcess;
    }
    public void setRatioProcess(Double ratioProcess) {
        this.ratioProcess = ratioProcess;
    }

    public Double getRatioExam() {
        return ratioExam;
    }
    public void setRatioExam(Double ratioExam) {
        this.ratioExam = ratioExam;
    }
}
