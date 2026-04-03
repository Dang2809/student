package com.example.student_management.controller;

import com.example.student_management.model.Student;
import com.example.student_management.service.StudentService;
import com.example.student_management.dto.StudentResponse;
import com.example.student_management.dto.StudentListResponse;
import com.example.student_management.dto.DeleteResponse;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public StudentResponse create(@Valid @RequestBody Student s, @RequestParam Long userId) {
        Student saved = service.create(s, userId);
        return new StudentResponse("Tạo sinh viên thành công", saved);
    }

    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable Long id) {
        Student student = service.getById(id);
        return new StudentResponse("Lấy thông tin sinh viên thành công", student);
    }

    @GetMapping("/search")
    public ResponseEntity<StudentResponse> getStudentByName(@RequestParam String name) {
        Student student = service.getByName(name);
        return ResponseEntity.ok(new StudentResponse("Lấy thông tin sinh viên thành công", student));
    }

    @GetMapping("/me")
    public ResponseEntity<Student> getMyStudent() {
        Student student = service.getMyStudent();
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public StudentListResponse getAll() {
        List<Student> students = service.getAll();
        List<StudentResponse> studentResponses = students.stream()
                .map(st -> new StudentResponse(null, st))
                .toList();
        return new StudentListResponse("Lấy danh sách sinh viên thành công", studentResponses);
    }

    @PutMapping("/{id}")
    public StudentResponse update(@Valid @PathVariable Long id, @Valid @RequestBody Student s) {
        Student updated = service.update(id, s);
        return new StudentResponse("Cập nhật sinh viên thành công", updated);
    }

    @DeleteMapping("/{id}")
    public DeleteResponse delete(@PathVariable Long id) {
        service.delete(id);
        return new DeleteResponse("Xóa sinh viên thành công", id);
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return service.getStats();
    }


}
