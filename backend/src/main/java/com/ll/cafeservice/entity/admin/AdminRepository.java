package com.ll.cafeservice.entity.admin;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByUsername(String username);

    Optional<Object> findByUsername(String testAdmin);
}