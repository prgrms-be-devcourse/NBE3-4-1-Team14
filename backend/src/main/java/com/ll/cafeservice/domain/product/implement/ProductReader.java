package com.ll.cafeservice.domain.product.implement;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import com.ll.cafeservice.global.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductDetailRepository productRepository;

    public List<Product> findAllActivateProduct() {
        return productRepository.findAllActivateProduct().stream()
                .map(productDetail -> Product.builder()
                        .id(productDetail.getId())
                        .name(productDetail.getName())
                        .description(productDetail.getDescription())
                        .price(productDetail.getPrice())
                        .quantity(productDetail.getQuantity())
                        .imgFilename(productDetail.getImgFilename())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .map(productDetail -> Product.builder()
                        .id(productDetail.getId())
                        .name(productDetail.getName())
                        .description(productDetail.getDescription())
                        .price(productDetail.getPrice())
                        .quantity(productDetail.getQuantity())
                        .imgFilename(productDetail.getImgFilename())
                        .build()
                )
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}