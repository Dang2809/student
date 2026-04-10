package com.example.student_management.controller;

import com.example.student_management.dto.PointResponse;
import com.example.student_management.model.Point;
import com.example.student_management.repository.SubjectRepository;
import com.example.student_management.service.PointService;
import com.example.student_management.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointController {

    @Autowired
    private PointService service;

    @Autowired
    private SubjectRepository subjectRepo;

    // Tạo điểm mới
    @PostMapping
    public ApiResponse<PointResponse> create(@RequestBody Point p, @RequestParam Long subjectId) {
        Point saved = service.create(p, subjectId);
        return new ApiResponse<>(200, "Tạo điểm thành công", new PointResponse(saved));
    }

    // Lấy bảng điểm theo sinh viên
    /**@GetMapping("/student/{id}")
    public ApiResponse<List<PointResponse>> getByStudent(@PathVariable Long id) {
        List<PointResponse> points = service.getByStudent(id);
        return new ApiResponse<>(200, "Lấy bảng điểm thành công", points);
    }**/

    @GetMapping("/student/{id}")
    public ApiResponse<List<PointResponse>> getByStudent(
            @PathVariable Long id,
            @RequestParam(required = false) Integer semester) {
        List<PointResponse> points = (semester == null)
                ? service.getByStudent(id)
                : service.getByStudentAndSemester(id, semester);
        return new ApiResponse<>(200, "Lấy bảng điểm thành công", points);
    }


    // Lấy điểm theo pointId
    @GetMapping("/{pointId}")
    public ApiResponse<PointResponse> getPoint(@PathVariable Long pointId) {
        Point point = service.findById(pointId);
        return new ApiResponse<>(200, "Lấy điểm thành công", new PointResponse(point));
    }

    // Sửa điểm
    @PutMapping("/{pointId}")
    public ApiResponse<PointResponse> updatePoint(@PathVariable Long pointId, @RequestBody Point p) {
        Point updated = service.update(pointId, p);
        return new ApiResponse<>(200, "Cập nhật điểm thành công", new PointResponse(updated));
    }

    // Xóa điểm
    @DeleteMapping("/{pointId}")
    public ApiResponse<Long> deletePoint(@PathVariable Long pointId) {
        service.delete(pointId);
        return new ApiResponse<>(200, "Xóa điểm thành công", pointId);
    }
}
