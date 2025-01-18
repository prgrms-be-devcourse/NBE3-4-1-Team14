package com.ll.cafeservice.entity.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ll.cafeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "order_id")
    @Column
    private Long id; // 주문자 id

    @Column(name = "email", nullable = false)
    private String email; // 주문자 email

    @Column(name = "address", nullable = false)
    private String address;         // 배송 주소

    @Column(name = "pw", nullable = false)
    private int pw;

    @Column(name="order_uuid")
    private UUID orderUuid;

    @Column(name = "order_at")
    private LocalDateTime orderDateTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double totalPrice;     // 총 가격

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public void addOrderItem(OrderItem orderItem) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(orderItem);
        orderItem.setOrder(this); // 양방향 연관관계 설정
    }
}
