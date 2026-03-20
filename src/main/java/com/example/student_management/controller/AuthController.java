package com.example.student_management.controller;

import com.example.student_management.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return service.login(username, password);
    }
}