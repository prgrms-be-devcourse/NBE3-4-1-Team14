package com.ll.cafeservice.entity.order;

import com.ll.cafeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id; // 주문자 id

    @Column(name = "email", nullable = false, unique = true)
    private String email; // 주문자 email

    @Column(name = "address", nullable = false)
    private String address;         // 배송 주소


    private double totalPrice;     // 총 가격

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
