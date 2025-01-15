package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import com.ll.cafeservice.entity.product.productImage.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Product 도메인에 대한 추가 / 삭제 / 수정 작업을 맡는다.
 */
@RequiredArgsConstructor
@Component
public class ProductManager {

    private final ProductImageRepository productImageRepository;
    private final ProductDetailRepository productRepository;

    public void addProduct(NewProduct product) {
        ProductDetail productDetail = new ProductDetail(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                null

        );
        productRepository.save(productDetail);
    }
}
//api만들기, 테이블 설계