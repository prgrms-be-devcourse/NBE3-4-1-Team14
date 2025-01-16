package com.ll.cafeservice.domain.product.implement;
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
                .map(productDetail -> new Product(

                        productDetail.getId(),
                        productDetail.getName(),
                        productDetail.getDescription(),
                        productDetail.getPrice(),
                        productDetail.getQuantity(),
                        productDetail.getImageUrl()

                ))
                .collect(Collectors.toList());
    }

}