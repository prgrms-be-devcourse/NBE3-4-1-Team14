package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Product 도메인에 대한 추가 / 삭제 / 수정 작업을 맡는다.
 */
@RequiredArgsConstructor
@Component
public class ProductManager {

    private final ProductDetailRepository productDetailRepository;

    public long addProduct(NewProduct product) {
        ProductDetail productDetail = new ProductDetail(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                null
        );
        ProductDetail savedProduct = productDetailRepository.save(productDetail);

        return savedProduct.getId();
    }
}
//api만들기, 테이블 설계