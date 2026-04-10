package com.example.student_management.service;

import com.example.student_management.model.Subject;
import com.example.student_management.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository repo;

    public Subject create(Subject s) {
        return repo.save(s);
    }

    public List<Subject> getAll() {
        return repo.findAll();
    }

    public Subject getSubjectById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));
    }

    public Subject updateSubject(Long id, Subject updatedSubject) {
        Subject subject = getSubjectById(id);
        subject.setName(updatedSubject.getName());
        subject.setCredits(updatedSubject.getCredits());
        subject.setSemester(updatedSubject.getSemester());
        subject.setRatioProcess(updatedSubject.getRatioProcess());
        subject.setRatioExam(updatedSubject.getRatioExam());
        return repo.save(subject);
    }

    // Xóa môn học
    public void deleteSubject(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Không tìm thấy môn học để xóa");
        }
        repo.deleteById(id);
    }

}
