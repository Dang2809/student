package com.example.student_management.controller;

import com.example.student_management.dto.ApiResponse;
import com.example.student_management.model.Subject;
import com.example.student_management.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService service;

    // Tạo môn học mới
    @PostMapping
    public ApiResponse<Subject> create(@RequestBody Subject s) {
        Subject created = service.create(s);
        return new ApiResponse<>(200, "Tạo môn học thành công", created);
    }

    // Lấy tất cả môn học
    @GetMapping
    public ApiResponse<List<Subject>> getAll() {
        List<Subject> subjects = service.getAll();
        return new ApiResponse<>(200, "Lấy danh sách môn học thành công", subjects);
    }

    // Lấy môn học theo ID
    @GetMapping("/{id}")
    public ApiResponse<Subject> getSubjectById(@PathVariable Long id) {
        Subject subject = service.getSubjectById(id);
        return new ApiResponse<>(200, "Lấy môn học thành công", subject);
    }

    // Cập nhật môn học
    @PutMapping("/{id}")
    public ApiResponse<Subject> updateSubject(
            @PathVariable Long id,
            @RequestBody Subject updatedSubject) {
        Subject subject = service.updateSubject(id, updatedSubject);
        return new ApiResponse<>(200, "Cập nhật môn học thành công", subject);
    }

    // Xóa môn học theo ID
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSubject(@PathVariable Long id) {
        service.deleteSubject(id);
        return new ApiResponse<>(200, "Xóa môn học thành công", null);
    }

}
