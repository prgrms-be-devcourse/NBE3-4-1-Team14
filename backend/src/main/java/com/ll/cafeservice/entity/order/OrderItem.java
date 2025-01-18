package com.ll.cafeservice.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ll.cafeservice.entity.base.BaseEntity;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id; // 주문항목 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "product_id", nullable = false)
    private ProductDetail product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    private int quantity; // 수량

    private long price; // 가격

    public void calculateTotalPrice(){
        this.price = quantity*product.getPrice();
    }
}
