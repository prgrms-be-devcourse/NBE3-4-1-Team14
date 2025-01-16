package com.ll.cafeservice.entity.product.product;

import com.ll.cafeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ProductDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;            // 제품 이름

    private String description;     // 제품 설명

    private Integer price;          // 가격

    private Integer quantity;       // 수량

    private Long imageId;           // 제품 이미지

    protected ProductDetail() {
    }

    public ProductDetail(String name, String description, Integer price, Integer quantity, Long imageId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageId = imageId;
    }

}
