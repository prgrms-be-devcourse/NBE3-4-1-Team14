package com.ll.cafeservice.domain.product;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
@Builder
public class Product {
    private final Long id;                // 제품 ID
    private final String name;            // 제품 이름
    private final String description;     // 제품 설명
    private final Integer price;           // 가격
    private final Integer quantity;
    private final String imageUrl; // 제품 이미지 목록


    public Product updateProduct(String name, Integer price, String description, Integer quantity) {
        return new Product(
                this.id,
                name,
                description,
                price,
                quantity,
                this.imageUrl
        );
    }
}


