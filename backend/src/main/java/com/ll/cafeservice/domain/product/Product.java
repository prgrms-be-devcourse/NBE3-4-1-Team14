package com.ll.cafeservice.domain.product;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;


@RequiredArgsConstructor
@Getter
@Builder
public class Product {
    private final Long id;                  // 제품 ID
    private final String name;              // 제품 이름
    private final String description;       // 제품 설명
    private final Integer price;            // 가격
    private final Integer quantity;
    private final String imgFilename;      // 제품 이미지 목록

    public Product updateProduct(String name, Integer price, String description, Integer quantity, String imgFilename) {
        return new Product(
                this.id,
                name,
                description,
                price,
                quantity,
                imgFilename


        );

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price &&
                quantity == product.quantity &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(imgFilename, product.imgFilename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, quantity, imgFilename);
    }
}



