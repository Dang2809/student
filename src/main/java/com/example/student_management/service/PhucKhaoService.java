package com.example.student_management.service;

import com.example.student_management.dto.PhucKhaoRequest;
import com.example.student_management.dto.PhucKhaoResponse;
import com.example.student_management.model.PhucKhao;
import com.example.student_management.model.Student;
import com.example.student_management.model.Subject;
import com.example.student_management.repository.PhucKhaoRepository;
import com.example.student_management.repository.StudentRepository;
import com.example.student_management.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhucKhaoService {

    @Autowired
    private PhucKhaoRepository repo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    public PhucKhaoResponse submitRequest(PhucKhaoRequest dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        // Kiểm tra trùng lặp
        boolean exists = repo.existsByStudentIdAndSubjectIdAndSemester(
                dto.getStudentId(), dto.getSubjectId(), dto.getSemester()
        );
        if (exists) {
            throw new RuntimeException("Môn học này đã có yêu cầu phúc khảo, không thể gửi thêm");
        }

        PhucKhao pk = new PhucKhao();
        pk.setStudent(student);
        pk.setSubject(subject);
        pk.setSemester(dto.getSemester());
        pk.setCurrentScore(dto.getCurrentScore());
        pk.setLyDo(dto.getLyDo());
        pk.setStatus(PhucKhao.Status.PENDING);

        return new PhucKhaoResponse(repo.save(pk));
    }

    public List<PhucKhaoResponse> listByStudent(Long studentId) {
        return repo.findByStudentId(studentId).stream()
                .map(PhucKhaoResponse::new)
                .toList();
    }

    public List<PhucKhaoResponse> listPending() {
        return repo.findByStatus(PhucKhao.Status.PENDING).stream()
                .map(PhucKhaoResponse::new)
                .toList();
    }

    public List<PhucKhaoResponse> listAll() {
        return repo.findAll().stream()
                .map(PhucKhaoResponse::new)
                .toList();
    }


    public PhucKhaoResponse review(Long id, PhucKhao.Status status, String phanHoi) {
        PhucKhao pk = repo.findById(id).orElseThrow();
        pk.setStatus(status);
        pk.setPhanHoi(phanHoi);
        return new PhucKhaoResponse(repo.save(pk));
    }
}

