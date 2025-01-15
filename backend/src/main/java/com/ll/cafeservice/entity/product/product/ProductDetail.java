package com.ll.cafeservice.entity.product.product;

import com.ll.cafeservice.entity.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class ProductDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;            // 제품 이름

    private String description;     // 제품 설명

    private double price;           // 가격

    private Long imageId;           // 제품 이미지
}
