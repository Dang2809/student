package com.example.student_management.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Khóa bí mật dùng để ký và xác thực JWT
    private final String secret = "secret123secret123secret123secret123secret123secret123";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Lấy header Authorization từ request
        String header = request.getHeader("Authorization");

        // Kiểm tra xem header có tồn tại và bắt đầu bằng "Bearer "
        if (header != null && header.startsWith("Bearer ")) {
            // Cắt chuỗi để lấy token JWT (bỏ chữ "Bearer ")
            String token = header.substring(7);

            try {
                // Giải mã và xác thực token bằng secret key
                Claims claims = Jwts.parser()
                        .setSigningKey(secret) // thiết lập khóa bí mật
                        .parseClaimsJws(token) // parse token
                        .getBody(); // lấy phần body chứa thông tin (claims)

                // Lấy username từ claim "subject"
                String username = claims.getSubject();

                // Lấy danh sách roles từ claim "roles"
                List<String> roles = claims.get("roles", List.class);

                // Chuyển roles thành danh sách quyền (authorities) của Spring Security
                var authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // Tạo đối tượng Authentication để lưu vào SecurityContext
                var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // Nếu token không hợp lệ thì bỏ qua, Spring Security sẽ tự chặn request
            }
        }

        // Tiếp tục chuỗi filter (chuyển request sang filter tiếp theo)
        filterChain.doFilter(request, response);
    }
}
