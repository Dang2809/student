package com.example.student_management.repository;

import com.example.student_management.model.PhucKhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.student_management.dto.PhucKhaoResponse;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface PhucKhaoRepository extends JpaRepository<PhucKhao, Long> {
    List<PhucKhao> findByStudentId(Long studentId);
    List<PhucKhao> findByStatus(PhucKhao.Status status);
    boolean existsByStudentIdAndSubjectIdAndSemester(Long studentId, Long subjectId, int semester);

    /**@Query("SELECT new com.example.student_management.dto.FDTO(p.phanHoi, p.status)" +
            "FROM PhucKhao p WHERE p.student.id = :studentId")
    List<FeedbackDTO> findFeedbackByStudent(Long studentId);**/

    @Query("""
    SELECT new com.example.student_management.dto.PhucKhaoResponse(
        p.id,
        s.id,
        sub.id,
        s.fullName,
        sub.name,
        p.semester,
        p.currentScore,
        p.lyDo,
        CAST(p.status as string),
        p.phanHoi,
        CONCAT(CAST(p.status as string), ' - ', COALESCE(p.phanHoi, '')),
        p.createdAt,
        p.updatedAt
    )
    FROM PhucKhao p
    JOIN p.student s
    JOIN p.subject sub
    WHERE s.id = :studentId
""")
    List<PhucKhaoResponse> findByStudentIdWithSummary(Long studentId);
}

