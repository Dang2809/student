package com.example.student_management.service;

import com.example.student_management.model.Role;
import com.example.student_management.model.User;
import com.example.student_management.repository.RoleRepository;
import com.example.student_management.repository.UserRepository;
import com.example.student_management.security.JwtUtil;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final JwtUtil jwt;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepo,
                       RoleRepository roleRepo,
                       JwtUtil jwt) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.jwt = jwt;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // LOGIN
    public String login(String username, String password) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Người dùng không tồn tại"));

        // check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Sai tài khoản hoặc mật khẩu");
        }

        // check trạng thái
        if ("PENDING".equals(user.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Tài khoản của bạn đang chờ duyệt");
        }

        if ("REJECTED".equals(user.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Tài khoản của bạn đã bị từ chối");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Trạng thái tài khoản không hợp lệ");
        }

        return jwt.generateToken(user);
    }

    // REGISTER
    @CacheEvict(value = "all_users", allEntries = true)
    public void register(String username, String password) {

        User user = new User();

        user.setUsername(username);

        // encode password
        user.setPassword(passwordEncoder.encode(password));

        Role defaultRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy role mặc định"));

        user.setRoles(Set.of(defaultRole));

        user.setStatus("PENDING");

        userRepo.save(user);
    }

    // PROMOTE ADMIN
    @CacheEvict(value = "all_users", allEntries = true)
    public void promoteToAdmin(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User không tồn tại"));

        Role adminRole = roleRepo.findByName("ROLE_ADMIN")
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy role ADMIN"));

        Set<Role> roles = user.getRoles();

        roles.removeIf(role ->
                role.getName().equals("ROLE_USER"));

        roles.add(adminRole);

        user.setRoles(roles);

        userRepo.save(user);
    }

    // APPROVE USER
    @CacheEvict(value = "all_users", allEntries = true)
    public void approveUser(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User không tồn tại"));

        user.setStatus("ACTIVE");

        userRepo.save(user);
    }

    // REJECT USER
    @CacheEvict(value = "all_users", allEntries = true)
    public void rejectUser(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User không tồn tại"));

        user.setStatus("REJECTED");

        userRepo.save(user);
    }

    // CACHE DANH SÁCH USER
    @Cacheable(value = "all_users")
    public List<User> getAllUsers() {

        System.out.println("LOAD ALL USERS FROM MYSQL");

        return userRepo.findAll();
    }
}