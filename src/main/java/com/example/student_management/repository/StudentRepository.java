package com.example.student_management.repository;

import com.example.student_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.student_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface StudentRepository extends JpaRepository<Student, Long> {
    long countByGender(String gender);
    Optional<Student> findByFullName(String fullName);
    Optional<Student> findByUserId(Long userId);


    @Query("SELECT s.address, COUNT(s) FROM Student s WHERE s.address IS NOT NULL GROUP BY s.address ORDER BY COUNT(s) DESC")
    List<Object[]> findTopAddresses();

}