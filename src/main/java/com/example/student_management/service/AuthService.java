/**package com.example.student_management.service;

import com.example.student_management.model.User;
import com.example.student_management.model.Role;
import com.example.student_management.repository.RoleRepository;
import com.example.student_management.repository.UserRepository;
import com.example.student_management.security.EncryptionUtil;
import com.example.student_management.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final JwtUtil jwt;

    public AuthService(UserRepository userRepo, RoleRepository roleRepo, JwtUtil jwt) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.jwt = jwt;
    }

    // Login: mã hóa cả input và mật khẩu trong DB rồi so sánh
    public String login(String username, String password) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        String encryptedInput = EncryptionUtil.encrypt(password);
        String encryptedDbPassword = EncryptionUtil.encrypt(user.getPassword());

        if (!encryptedInput.equals(encryptedDbPassword)) {
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu");
        }

        return jwt.generateToken(user);
    }

    // Register: lưu mật khẩu thường, không mã hóa
    public void register(String username, String password) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Tên người dùng đã tồn tại");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // lưu plaintext

        Role defaultRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role mặc định"));

        user.setRoles(Set.of(defaultRole));
        userRepo.save(user);
    }
}**/

package com.example.student_management.service;

import com.example.student_management.model.User;
import com.example.student_management.model.Role;
import com.example.student_management.repository.RoleRepository;
import com.example.student_management.repository.UserRepository;
import com.example.student_management.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final JwtUtil jwt;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepo, RoleRepository roleRepo, JwtUtil jwt) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.jwt = jwt;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String login(String username, String password) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        // So sánh mật khẩu thô với mật khẩu đã mã hóa trong DB
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu");
        }

        return jwt.generateToken(user);
    }

    public void register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(password));

        Role defaultRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role mặc định"));

        user.setRoles(Set.of(defaultRole));
        userRepo.save(user);
    }

    public void promoteToAdmin(String username) {
    User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User không tồn tại"));

    Role adminRole = roleRepo.findByName("ROLE_ADMIN")
            .orElseThrow(() -> new RuntimeException("Không tìm thấy role ADMIN"));

    // Xóa hết role cũ hoặc chỉ xóa ROLE_USER
    Set<Role> roles = user.getRoles();
    roles.removeIf(role -> role.getName().equals("ROLE_USER"));

    // Thêm role ADMIN
    roles.add(adminRole);
    user.setRoles(roles);

    userRepo.save(user);
} 
}