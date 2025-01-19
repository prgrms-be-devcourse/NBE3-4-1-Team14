package com.ll.cafeservice.entity.product.product;

import com.ll.cafeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;                    // 제품 이름
    private String description;             // 제품 설명
    private Integer price;                  // 가격
    private Integer quantity;               // 수량
    private String imgFilename;             // 제품 이미지

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public void update(String name, String description, Integer price, Integer quantity, String imgFilename) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imgFilename = imgFilename;
    }

    public void deactivate(){
        this.status = ProductStatus.INACTIVE;
    }

}
