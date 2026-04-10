package com.example.student_management.service;

import com.example.student_management.dto.PointResponse;
import com.example.student_management.model.Point;
import com.example.student_management.model.Subject;
import com.example.student_management.repository.PointRepository;
import com.example.student_management.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PointService {
    @Autowired
    private PointRepository repo;

    @Autowired
    private SubjectRepository subjectRepo;

    public Point create(Point point, Long subjectId) {

        //Check trùng
        if (repo.existsByStudentIdAndSubject_Id(point.getStudentId(), subjectId)) {
            throw new RuntimeException("Sinh viên đã có điểm cho môn học này!");
        }

        //Lấy môn học
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        //Gán subject vào point
        point.setSubject(subject);

        //Tính điểm
        point.calculateFinal();

        //Lưu
        return repo.save(point);
    }

    // Trả về danh sách điểm kèm tên môn học
    public List<PointResponse> getByStudent(Long studentId) {
        List<Point> points = repo.findByStudentId(studentId);
        return points.stream()
                .map(PointResponse::new)
                .toList();
    }

    public List<PointResponse> getByStudentAndSemester(Long studentId, Integer semester) {
        return repo.findByStudentIdAndSubjectSemester(studentId, semester)
                .stream()
                .map(PointResponse::new)
                .toList();
    }

    public Point findById(Long pointId) {
        return repo.findById(pointId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy điểm"));
    }

    public Point update(Long pointId, Point newData) {
        Point existing = findById(pointId);
        existing.setProcessScore(newData.getProcessScore());
        existing.setExamScore(newData.getExamScore());
        existing.calculateFinal();
        return repo.save(existing);
    }

    public void delete(Long pointId) {
        Point existing = findById(pointId); // dùng lại hàm findById để kiểm tra tồn tại
        repo.delete(existing);
    }
}
