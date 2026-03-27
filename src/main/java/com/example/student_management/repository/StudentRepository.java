package com.example.student_management.repository;

import com.example.student_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.student_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface StudentRepository extends JpaRepository<Student, Long> {
    long countByGender(String gender);
    Optional<Student> findByFullName(String fullName);
}