package com.example.student_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApplicationTests {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTests.class, args);

        // Sinh mật khẩu mã hóa để kiểm tra
        String encoded = new BCryptPasswordEncoder().encode("123");
        System.out.println("BCrypt của 123 là: " + encoded);

        
    }
}
