package com.example.student_management.security;

import com.example.student_management.model.User;
import com.example.student_management.model.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtil {
    private final String secret = "secret123secret123secret123secret123secret123secret123";

    public String generateToken(User user) {
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        if (roles.contains("ROLE_ADMIN")) {
        return Jwts.builder()
            .setSubject("ADMIN")
            .claim("roles", roles)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
        } else {
        return Jwts.builder()
            .setSubject(user.getUsername()) 
            .claim("roles", roles)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
        }
    }
}