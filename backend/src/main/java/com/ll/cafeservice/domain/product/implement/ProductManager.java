package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import com.ll.cafeservice.entity.product.productImage.ProductImageRepository;
import org.springframework.stereotype.Component;

/**
 * Product 도메인에 대한 추가 / 삭제 / 수정 작업을 맡는다.
 */

@Component
public class ProductManager {

    private ProductImageRepository productImageRepository;
    private ProductDetailRepository productRepository;

    public void addProduct(NewProduct product) {
        // TODO : NewProduct 객체의 정보를 적절히 분배하여 Repository 안에 저장한다.

    }
}
