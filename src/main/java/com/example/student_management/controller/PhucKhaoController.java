package com.example.student_management.controller;

import com.example.student_management.dto.PhucKhaoRequest;
import com.example.student_management.dto.PhucKhaoResponse;
import com.example.student_management.dto.ApiResponse;
import com.example.student_management.model.PhucKhao;
import com.example.student_management.service.PhucKhaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phuckhao")
public class PhucKhaoController {

    @Autowired
    private PhucKhaoService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PhucKhaoResponse>> submit(@RequestBody PhucKhaoRequest req) {
        PhucKhaoResponse response = service.submitRequest(req);
        return ResponseEntity.ok(new ApiResponse<>(200, "Yêu cầu phúc khảo đã gửi", response));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<ApiResponse<List<PhucKhaoResponse>>> listByStudent(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Danh sách phúc khảo của sinh viên", service.listByStudent(id)));
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<PhucKhaoResponse>>> listPending() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Danh sách yêu cầu pending", service.listPending()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PhucKhaoResponse>>> listAll() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Danh sách tất cả yêu cầu phúc khảo", service.listAll()));
    }

    @PostMapping("/review")
    public ResponseEntity<ApiResponse<PhucKhaoResponse>> review(
            @RequestParam Long id,
            @RequestParam PhucKhao.Status status,
            @RequestParam(required=false) String phanHoi) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Đã cập nhật trạng thái", service.review(id, status, phanHoi)));
    }
}
