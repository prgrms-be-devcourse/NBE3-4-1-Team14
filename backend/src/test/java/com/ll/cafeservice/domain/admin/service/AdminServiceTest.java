package com.ll.cafeservice.domain.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ll.cafeservice.entity.admin.Admin;
import com.ll.cafeservice.entity.admin.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

    private Admin admin;
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setup() {
        adminRepository.deleteAll();
        encoder = new BCryptPasswordEncoder();
        admin = Admin.builder()
            .username("testAdmin")
            .password("1234")
            .build();
        admin.encodePassword(encoder);
    }

    @Test
    @DisplayName("데이터 삽입 테스트")
    void testInsertAdminData() {
        Admin createdAdmin = adminService.createAdmin("testAdmin", "1234");
        assertEquals(admin.getUsername(), createdAdmin.getUsername());
        assertTrue(encoder.matches("1234", createdAdmin.getPassword()));
    }

    @Test
    @DisplayName("삽입된 데이터 검증 테스트")
    void testSavedAdminData() {
        adminService.createAdmin("testAdmin", "1234");
        Admin foundAdmin = (Admin) adminRepository.findByUsername("testAdmin").orElseThrow();
        assertEquals(admin.getUsername(), foundAdmin.getUsername());
        assertTrue(encoder.matches("1234", foundAdmin.getPassword()));
    }
}
