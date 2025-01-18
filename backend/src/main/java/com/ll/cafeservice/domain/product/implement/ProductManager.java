package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import com.ll.cafeservice.entity.product.product.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Product 도메인에 대한 추가 / 삭제 / 수정 작업을 맡는다.
 */
@RequiredArgsConstructor
@Component
public class ProductManager {

    private final ProductDetailRepository productDetailRepository;
    //제품추가
    public long addProduct(NewProduct product) {

        ProductDetail productDetail = ProductDetail.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .imgFilename(product.getImgFilename())
                .status(ProductStatus.ACTIVE)
                .build();

        ProductDetail savedProduct = productDetailRepository.save(productDetail);

        return savedProduct.getId();
    }
    //제품수정
    public void updatedProduct(Product updatedProduct) {

        // 데이터베이스에서 기존 제품 조회
        ProductDetail productDetail = productDetailRepository.findById(updatedProduct.getId())
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다. ID: " + updatedProduct.getId()));

        // 기존 제품 정보 업데이트
        productDetail.update(
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getQuantity(),
                updatedProduct.getImgFilename()
        );

        // 변경된 내용 저장
        productDetailRepository.save(productDetail);
    }

    // 제품 삭제
    public void deactivateProduct(Product product) {
        // 제품이 존재하는지 확인
        ProductDetail productDetail = productDetailRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다. ID: " + product.getId()));

        // 제품 활성화
        productDetail.deactivate();
        productDetailRepository.save(productDetail);
    }
}
