package com.example.student_management.controller;

import com.example.student_management.dto.ApiResponse;
import com.example.student_management.model.User;
import com.example.student_management.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    public static class AuthRequest {
        private String username;
        private String password;
        // getter & setter
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // API đăng nhập
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody AuthRequest request) {
        String token = service.login(request.getUsername(), request.getPassword());
        return new ApiResponse<>(200, "Đăng nhập thành công", token);
    }

    // API đăng ký
    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody AuthRequest request) {
        service.register(request.getUsername(), request.getPassword());
        return new ApiResponse<>(200, "Đăng ký thành công. Tài khoản đang chờ xét duyệt", "OK");
    }

    // Nâng quyền user thành admin
    @PostMapping("/promote/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> promoteToAdmin(@PathVariable String username) {
        service.promoteToAdmin(username);
        return new ApiResponse<>(200, "Nâng quyền thành công", username);
    }

    // Duyệt user
    @PostMapping("/approve/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> approveUser(@PathVariable String username) {
        service.approveUser(username);
        return new ApiResponse<>(200, "Duyệt user thành công", username);
    }

    // Từ chối user
    @PostMapping("/reject/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> rejectUser(@PathVariable String username) {
        service.rejectUser(username);
        return new ApiResponse<>(200, "Từ chối user thành công", username);
    }

    // Lấy danh sách user
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return new ApiResponse<>(200, "Lấy danh sách user thành công", users);
    }
}
