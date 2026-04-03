package com.example.student_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // BẮT BUỘC phải có
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // disable csrf cho API
                .csrf(csrf -> csrf.disable())

                // phân quyền
                .authorizeHttpRequests(auth -> auth
                        // cho phép preflight request
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // public API
                        .requestMatchers("/login", "/register", "/auth/**").permitAll()
                        .requestMatchers("/students/stats").permitAll()
                        .requestMatchers("/students/me").permitAll()

                        // cần login
                        .requestMatchers("/students/**").authenticated()
                        .requestMatchers("/subjects/**").authenticated()
                        .requestMatchers("/points/**").authenticated()
                        .anyRequest().authenticated()
                )

                // thêm JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS CONFIG CHUẨN
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // frontend của bạn
        config.setAllowedOrigins(List.of("http://localhost:5173"));

        // method cho phép
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // header cho phép
        config.setAllowedHeaders(List.of("*"));

        // cho phép gửi token (Authorization)
        config.setAllowCredentials(true);

        // QUAN TRỌNG nếu dùng Authorization header
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}