package com.example.student_management.security;

import com.example.student_management.model.User;
import com.example.student_management.model.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtil {
    // Khóa bí mật dùng để ký và xác thực JWT
    private final String secret = "secret123secret123secret123secret123secret123secret123";

    // Hàm sinh ra JWT token từ thông tin User
    public String generateToken(User user) {
        // Lấy danh sách tên role từ đối tượng User
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName) // lấy tên role
                .toList();

        // Nếu user có role là ADMIN thì subject sẽ là "ADMIN"
        if (roles.contains("ROLE_ADMIN")) {
            return Jwts.builder()
                .setSubject(user.getUsername())// subject là ADMIN
                .claim("roles", roles) // thêm claim roles vào token
                .signWith(SignatureAlgorithm.HS256, secret) // ký token bằng thuật toán HS256 và secret key
                .compact(); // tạo token dạng chuỗi
        } else {
            // Nếu không phải ADMIN thì subject là username của user
            return Jwts.builder()
                .setSubject(user.getUsername()) // subject là username
                    .claim("roles", roles)// thêm claim roles vào token
                    .claim("userId", user.getId())
                .signWith(SignatureAlgorithm.HS256, secret) // ký token
                .compact(); // tạo token
        }
    }
}
