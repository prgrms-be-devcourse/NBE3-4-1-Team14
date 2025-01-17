package com.ll.cafeservice.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
//    List<Order> findByEmail(String email);
    List<Order> findByOrderUuid(UUID orderUuid);
}
