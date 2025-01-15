package com.ll.cafeservice.entity.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Long> {
    List<DeliveryDetail> findByEmail(String email);
}
