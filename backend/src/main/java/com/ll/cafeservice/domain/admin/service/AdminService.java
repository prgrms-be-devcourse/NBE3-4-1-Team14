package com.ll.cafeservice.domain.admin.service;

import com.ll.cafeservice.entity.admin.Admin;
import com.ll.cafeservice.entity.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin createAdmin(String username, String rawPassword) {
        Admin admin = Admin.builder()
            .username(username)
            .password(rawPassword)
            .build();
        admin.encodePassword(passwordEncoder);
        return adminRepository.save(admin);
    }

    public void createAdminAccountIfNotExist() {
        if (!adminRepository.existsByUsername(adminUsername)) {
            createAdmin(adminUsername, adminPassword);
        }
    }
}