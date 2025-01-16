package com.ll.cafeservice.domain.product;

import com.ll.cafeservice.entity.product.productImage.ProductImage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@Getter
public class Product {
    private final Long id;                // 제품 ID
    private final String name;            // 제품 이름
    private final String description;     // 제품 설명
    private final Integer price;           // 가격
    private final Integer quantity;
    private final List<ProductImage> images; // 제품 이미지 목록


}
