package com.ll.cafeservice.entity.order;

import com.ll.cafeservice.entity.base.BaseEntity;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정 추가
    @JoinColumn(name = "order_id" , nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정 추가
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDetail product;

    private int quantity;

    private double price;

    // 생성자 추가
    public OrderItem(Order order, ProductDetail product, int quantity, double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}