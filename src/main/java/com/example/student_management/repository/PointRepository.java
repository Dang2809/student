package com.example.student_management.repository;

import com.example.student_management.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByStudentId(Long studentId);
    boolean existsByStudentIdAndSubject_Id(Long studentId, Long subjectId);
}
