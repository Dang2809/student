package com.example.student_management.repository;

import com.example.student_management.model.PhucKhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhucKhaoRepository extends JpaRepository<PhucKhao, Long> {
    List<PhucKhao> findByStudentId(Long studentId);
    List<PhucKhao> findByStatus(PhucKhao.Status status);
    boolean existsByStudentIdAndSubjectIdAndSemester(Long studentId, Long subjectId, int semester);
}

