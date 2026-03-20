package com.example.student_management.service;

import com.example.student_management.model.User;
import com.example.student_management.repository.UserRepository;
import com.example.student_management.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repo;
    private final JwtUtil jwt;

    public AuthService(UserRepository repo, JwtUtil jwt) {
        this.repo = repo;
        this.jwt = jwt;
    }

    public String login(String username, String password) {
    User user = repo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

            //
    if (!user.getPassword().equals(password)) {
        throw new RuntimeException("Sai tài khoản hoặc mật khẩu");
    }

    return jwt.generateToken(user);
}
}