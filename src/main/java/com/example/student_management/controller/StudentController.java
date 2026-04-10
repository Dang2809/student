/**package com.example.student_management.controller;

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
    public List<StudentResponse> getAll() {
        List<Student> students = service.getAll();
        return students.stream()
                .map(st -> new StudentResponse(null, st))
                .toList();
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


}**/

package com.example.student_management.controller;

import com.example.student_management.model.Student;
import com.example.student_management.service.StudentService;
import com.example.student_management.dto.StudentResponse;
import com.example.student_management.dto.DeleteResponse;
import com.example.student_management.dto.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // Thêm sinh viên mới
    @PostMapping
    public ApiResponse<StudentResponse> create(@Valid @RequestBody Student s, @RequestParam Long userId) {
        Student saved = service.create(s, userId);
        return new ApiResponse<>(200, "Tạo sinh viên thành công", new StudentResponse(saved));
    }

    // Lấy thông tin sinh viên theo ID
    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getById(@PathVariable Long id) {
        Student student = service.getById(id);
        return new ApiResponse<>(200, "Lấy thông tin sinh viên thành công", new StudentResponse(student));
    }

    // Tìm sinh viên theo tên
    @GetMapping("/search")
    public ApiResponse<StudentResponse> getStudentByName(@RequestParam String name) {
        Student student = service.getByName(name);
        return new ApiResponse<>(200, "Lấy thông tin sinh viên thành công", new StudentResponse(student));
    }

    // Lấy thông tin sinh viên của chính user đang đăng nhập
    @GetMapping("/me")
    public ApiResponse<StudentResponse> getMyStudent() {
        Student student = service.getMyStudent();
        return new ApiResponse<>(200, "Lấy thông tin sinh viên của bạn", new StudentResponse(student));
    }

    // Lấy tất cả danh sách sinh viên
    @GetMapping
    public ApiResponse<List<StudentResponse>> getAll() {
        List<Student> students = service.getAll();
        List<StudentResponse> dtoList = students.stream()
                .map(StudentResponse::new)
                .toList();
        return new ApiResponse<>(200, "Lấy danh sách sinh viên thành công", dtoList);
    }

    // Cập nhật sinh viên
    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> update(@Valid @PathVariable Long id, @Valid @RequestBody Student s) {
        Student updated = service.update(id, s);
        return new ApiResponse<>(200, "Cập nhật sinh viên thành công", new StudentResponse(updated));
    }

    // Xóa sinh viên
    @DeleteMapping("/{id}")
    public ApiResponse<DeleteResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return new ApiResponse<>(200, "Xóa sinh viên thành công", new DeleteResponse(id));
    }


    // Lấy thống kê sinh viên
    @GetMapping("/stats")
    public ApiResponse<Map<String, Object>> getStats() {
        Map<String, Object> stats = service.getStats();
        return new ApiResponse<>(200, "Lấy thống kê sinh viên thành công", stats);
    }

    @GetMapping("/top-addresses")
    public ApiResponse<List<Map<String, Object>>> getTopAddresses() {
        return new ApiResponse<>(200, "Top 5 địa phương có nhiều sinh viên nhất", service.getTopAddresses());
    }
}

