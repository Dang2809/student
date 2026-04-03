package com.example.student_management.controller;

import com.example.student_management.model.Subject;
import com.example.student_management.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService service;

    @PostMapping
    public Subject create(@RequestBody Subject s) {
        return service.create(s);
    }

    @GetMapping
    public List<Subject> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        Subject subject = service.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(
            @PathVariable Long id,
            @RequestBody Subject updatedSubject) {
        Subject subject = service.updateSubject(id, updatedSubject);
        return ResponseEntity.ok(subject);
    }
}
