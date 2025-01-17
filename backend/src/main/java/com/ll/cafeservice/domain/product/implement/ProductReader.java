package com.ll.cafeservice.domain.product.implement;
import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductDetailRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .map(productDetail -> Product.builder()
                        .id(productDetail.getId())
                        .name(productDetail.getName())
                        .description(productDetail.getDescription())
                        .price(productDetail.getPrice())
                        .quantity(productDetail.getQuantity())
                        .imageUrl(productDetail.getImageUrl())
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
                        .imageUrl(productDetail.getImageUrl())
                        .build()
                )
                .orElse(null);
    }
}