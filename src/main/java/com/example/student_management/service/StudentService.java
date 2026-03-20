package com.example.student_management.service;

import com.example.student_management.model.Student;
import com.example.student_management.model.User;
import com.example.student_management.repository.StudentRepository;
import com.example.student_management.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    private final UserRepository userRepo;

    public StudentService(StudentRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public Student create(Student s, Long userId) {
    // Lấy user từ DB theo userId (student sẽ gắn với user này)
    User targetUser = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

    // Lấy thông tin người đang đăng nhập từ token
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    if (!isAdmin) {
        throw new RuntimeException("Người dùng không được phép tạo sinh viên");
    }

    // Kiểm tra user đã có student chưa
    if (repo.findAll().stream()
            .anyMatch(st -> st.getUser().getId().equals(userId))) {
        throw new RuntimeException("Người dùng này đã có thông tin sinh viên");
    }
    s.setUser(targetUser);
    return repo.save(s);
}   

    public Student getById(Long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();

    User currentUser = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

    Student student = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Sinh viên này không tồn tại"));

    boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Bỏ qua kiểm tra quyền sở hữu nếu user có role ADMIN
    if (isAdmin) {
        return student;
    }

    // Nếu là USER thì chỉ được xem student của chính mình
    if (!student.getUser().getId().equals(currentUser.getId())) {
        throw new RuntimeException("Người dùng không có quyền xem thông tin sinh viên này");
    }
    return student;
}

    public List<Student> getAll() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    if (!isAdmin) {
        throw new RuntimeException("Người dùng không có quyền xem danh sách sinh viên");
    }
    return repo.findAll();
}

    public Student update(Long id, Student s) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    if (!isAdmin) {
        throw new RuntimeException("Người dùng không có quyền sửa thông tin sinh viên");
    }

    Student existing = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Sinh viên này không tồn tại"));
    existing.setFullName(s.getFullName());
    existing.setGender(s.getGender());
    existing.setDateOfBirth(s.getDateOfBirth());
    existing.setAddress(s.getAddress());
    return repo.save(existing);
}

    public void delete(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Người dùng không có quyền xóa sinh viên");
        }

        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Không thể xóa do ID của sinh viên " + id + " không tồn tại");
        }
        repo.deleteById(id);
    }
}
