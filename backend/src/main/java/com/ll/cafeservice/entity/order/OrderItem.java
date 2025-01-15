package com.ll.cafeservice.entity.order;

import com.ll.cafeservice.entity.base.BaseEntity;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import jakarta.persistence.*;

@Entity
public class OrderItem extends BaseEntity {
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItem_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDetail product;

    private int quantity; // 수량

    private double price; // 가격
    */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "orderItem_id")
private Long id; // 주문항목 id

    @Column(name = "order_id", nullable = false)
    private Long orderId; // 주문한사람들 id

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "product_id", nullable = false)
    private ProductDetail product;

    private int quantity; // 수량

    private double price; // 가격
}
