package com.example.student_management.service;

import com.example.student_management.model.Student;
import com.example.student_management.model.User;
import com.example.student_management.repository.StudentRepository;
import com.example.student_management.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Map;
import java.util.HashMap;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    private final UserRepository userRepo;

    public StudentService(StudentRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @CacheEvict(value = "students", allEntries = true)
    public Student create(Student s, Long userId) {
        User targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Người dùng không tồn tại"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Người dùng không được phép tạo sinh viên");
        }

            if (repo.findAll().stream().anyMatch(st -> st.getUser().getId().equals(userId))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Người dùng này đã có thông tin sinh viên");
        }

        // Check email
        if (repo.existsByEmail(s.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email đã tồn tại");
        }

        if (repo.existsByPhone(s.getPhone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Số điện thoại đã tồn tại");
        }

        s.setUser(targetUser);
        return repo.save(s);
    }

    public Student getById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User currentUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Người dùng không tồn tại"));

        Student student = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sinh viên này không tồn tại"));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !student.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Người dùng không có quyền xem thông tin sinh viên này");
        }

        return student;
    }

    public Student getByName(String name) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User currentUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Người dùng không tồn tại"));

        Student student = repo.findByFullName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sinh viên này không tồn tại"));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !student.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Người dùng không có quyền xem thông tin sinh viên này");
        }

        return student;
    }

    // Lấy student của chính user hiện tại (không cần truyền userId)
    public Student getMyStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User currentUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Người dùng không tồn tại"));

        return repo.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng này chưa có thông tin sinh viên"));
    }

    @Cacheable("students")
    public List<Student> getAll() {

        System.out.println("LOAD FROM MYSQL");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Người dùng không có quyền xem danh sách sinh viên");
        }
        return repo.findAll();
    }

    @CacheEvict(value = "students", allEntries = true)
    public Student update(Long id, Student s) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Người dùng không có quyền sửa thông tin sinh viên");
        }

        Student existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sinh viên này không tồn tại"));

        existing.setFullName(s.getFullName());
        existing.setGender(s.getGender());
        existing.setDateOfBirth(s.getDateOfBirth());
        existing.setAddress(s.getAddress());
        existing.setEmail(s.getEmail());
        existing.setPhone(s.getPhone());
        return repo.save(existing);
    }

    @CacheEvict(value = "students", allEntries = true)
    public void delete(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Người dùng không có quyền xóa sinh viên");
        }

        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không thể xóa do ID sinh viên " + id + " không tồn tại");
        }

        repo.deleteById(id);
    }

    // Lấy thống kê tổng quan
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", repo.count());
        stats.put("male", repo.countByGender("MALE"));
        stats.put("female", repo.countByGender("FEMALE"));
        return stats;
    }

    // Lấy top 5 địa chỉ
    public List<Map<String, Object>> getTopAddresses() {
        List<Object[]> results = repo.findTopAddresses();
        return results.stream()
                .limit(5)
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("address", (String) r[0]);
                    map.put("count", (Long) r[1]);
                    return map;
                })
                .toList();
    }
}
