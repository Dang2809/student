package com.example.student_management.controller;

import com.example.student_management.model.Student;
import com.example.student_management.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public Map<String, Object> create(@Valid @RequestBody Student s, @RequestParam Long userId) {
        Student saved = service.create(s, userId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Tạo sinh viên thành công");
        response.put("id", saved.getId());
        response.put("fullName", saved.getFullName());
        response.put("gender", saved.getGender());
        response.put("dateOfBirth", saved.getDateOfBirth());
        response.put("address", saved.getAddress());

        return response;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        Student student = service.getById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Lấy thông tin sinh viên thành công");
        response.put("id", student.getId());
        response.put("fullName", student.getFullName());
        response.put("gender", student.getGender());
        response.put("dateOfBirth", student.getDateOfBirth());
        response.put("address", student.getAddress());

        return response;
    }

    @GetMapping
    public Map<String, Object> getAll() {
        List<Student> students = service.getAll();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Lấy danh sách sinh viên thành công");

        // Trả về danh sách theo từng trường
        List<Map<String, Object>> studentList = new ArrayList<>();
        for (Student st : students) {
            Map<String, Object> studentMap = new HashMap<>();
            studentMap.put("id", st.getId());
            studentMap.put("fullName", st.getFullName());
            studentMap.put("gender", st.getGender());
            studentMap.put("dateOfBirth", st.getDateOfBirth());
            studentMap.put("address", st.getAddress());
            studentList.add(studentMap);
        }
        response.put("data", studentList);

        return response;
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@Valid @PathVariable Long id, @Valid @RequestBody Student s) {
        Student updated = service.update(id, s);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cập nhật sinh viên thành công");
        response.put("id", updated.getId());
        response.put("fullName", updated.getFullName());
        response.put("gender", updated.getGender());
        response.put("dateOfBirth", updated.getDateOfBirth());
        response.put("address", updated.getAddress());

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        service.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Xóa sinh viên thành công");
        response.put("id", id);

        return response;
    }
}