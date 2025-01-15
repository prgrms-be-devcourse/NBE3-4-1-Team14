package com.ll.cafeservice.entity.order;

import com.ll.cafeservice.entity.base.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String customerName;    // 주문자 이름
    private String address;         // 배송 주소
    private Integer totalPrice;     // 총 가격
    
    // Enum 등으로 OrderState 표현
 */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id; // 주문자 id

    @Column(name = "email", nullable = false, unique = true)
    private String email; // 주문자 email
    @Column(name = "address", nullable = false)
    private String address;         // 배송 주소
    private Integer totalPrice;     // 총 가격
}
