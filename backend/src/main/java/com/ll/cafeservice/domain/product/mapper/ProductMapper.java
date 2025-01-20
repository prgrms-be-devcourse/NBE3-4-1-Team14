package com.ll.cafeservice.domain.product.mapper;

import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.domain.product.dto.response.ProductInfoResponse;
import org.springframework.stereotype.Component;

@Component

public class ProductMapper {

    public ProductInfoResponse toResponse(Product product) {
        return  ProductInfoResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .filename(product.getImgFilename())
                .build();
    }
}
