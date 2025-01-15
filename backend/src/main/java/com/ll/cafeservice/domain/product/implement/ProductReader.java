package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import com.ll.cafeservice.entity.product.productImage.ProductImage;
import com.ll.cafeservice.entity.product.productImage.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductDetailRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public List<Product> findAll() {

        // Product 정보 불러오기
        List<ProductDetail> details = productRepository.findAll();
        List<ProductImage> images = productImageRepository.findAll();

        // Domain 객체로 변경 (변경 작업이 길면 ProductConverter? 와 같은 클래스로 뺄 수 있을 듯)
        // Product 객체로 변환
        List<Product> products = new ArrayList<>();
        for (ProductDetail productDetail : details) {
            // ProductDetail에 맞는 Product 객체 생성
            List<ProductImage> productImages = images.stream()
                    .filter(image -> image.getProduct().getId().equals(productDetail.getId())) // ProductDetail의 ID와 비교
                    .collect(Collectors.toList());

            Product product = new Product(
                    productDetail.getId(),
                    productDetail.getName(),
                    productDetail.getDescription(),
                    productDetail.getPrice(),
                    productImages
            );
            products.add(product);
        }

        // 3. 반환
        return products;
    }
}