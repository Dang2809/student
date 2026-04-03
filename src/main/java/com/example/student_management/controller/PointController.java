package com.example.student_management.controller;

import com.example.student_management.dto.PointResponse;
import com.example.student_management.model.Point;
import com.example.student_management.model.Subject;
import com.example.student_management.repository.SubjectRepository;
import com.example.student_management.service.PointService;
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

    @PostMapping
    public PointResponse create(@RequestBody Point p, @RequestParam Long subjectId) {
        Point saved = service.create(p, subjectId);
        return new PointResponse(saved);
    }

    @GetMapping("/student/{id}")
    public List<PointResponse> getByStudent(@PathVariable Long id) {
        return service.getByStudent(id);
    }

    // Lấy điểm theo pointId (để hiển thị form sửa)
    @GetMapping("/{pointId}")
    public PointResponse getPoint(@PathVariable Long pointId) {
        Point point = service.findById(pointId);
        return new PointResponse(point);
    }

    // Sửa điểm (dùng trực tiếp Point làm input)
    @PutMapping("/{pointId}")
    public PointResponse updatePoint(@PathVariable Long pointId, @RequestBody Point p) {
        Point updated = service.update(pointId, p);
        return new PointResponse(updated);
    }

    // Xóa điểm theo ID
    @DeleteMapping("/{pointId}")
    public void deletePoint(@PathVariable Long pointId) {
        service.delete(pointId);
    }
}