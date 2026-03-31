package com.example.student_management.controller;

import com.example.student_management.service.AuthService;
import com.example.student_management.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:5173") // frontend port
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    // DTO cho login/register
    public static class AuthRequest {
        private String username;
        private String password;

        // getter & setter
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

    // API đăng nhập
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return service.login(request.getUsername(), request.getPassword());
    }

    // API đăng ký
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        service.register(request.getUsername(), request.getPassword());
        return "Đăng ký thành công!";
    }

    // API nâng quyền user thành admin (chỉ admin mới được gọi)
    @PostMapping("/promote/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public String promoteToAdmin(@PathVariable String username) {
        service.promoteToAdmin(username);
        return "Người dùng " + username + " đã được nâng thành ADMIN!";
    }
    // API dành cho admin: duyệt tài khoản user (chuyển status từ PENDING sang ACTIVE)
    @PostMapping("/approve/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveUser(@PathVariable String username) {
        service.approveUser(username);
        return "Người dùng " + username + " đã được duyệt!";
    }

    // API dành cho admin: từ chối tài khoản user (chuyển status từ PENDING sang REJECTED)
    @PostMapping("/reject/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public String rejectUser(@PathVariable String username) {
        service.rejectUser(username);
        return "Người dùng " + username + " đã bị từ chối!";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }
}
