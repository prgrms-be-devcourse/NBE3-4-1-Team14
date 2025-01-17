package com.ll.cafeservice.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem>findByOrderId(Long orderId);
    @Query("SELECT orderItem FROM OrderItem orderItem WHERE orderItem.order.email = :email")
    List<OrderItem> findByEmail(@Param("email") String email);
}
