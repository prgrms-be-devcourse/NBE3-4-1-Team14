package com.ll.cafeservice.domain.product.service;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.domain.product.dto.request.ProductCreateRequest;
import com.ll.cafeservice.domain.product.dto.response.ProductCreateResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductInfoResponse;
import com.ll.cafeservice.domain.product.implement.ProductManager;
import com.ll.cafeservice.domain.product.implement.ProductReader;
import com.ll.cafeservice.domain.product.implement.ProductValidator;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductManager productManager;
    private final ProductReader productReader;
    private final ProductValidator productValidator;
    private final ProductDetailRepository productDetailRepository;

    public ProductCreateResponse addProduct(final ProductCreateRequest request) {

        // 1. Request 정보를 이용하여 새로운 품목 (NewProduct 클래스)를 생성
        NewProduct newProduct = new NewProduct(
                request.name(),
                request.price(),
                request.description(),
                request.quantity()
        );

        // 2. ProductManager 클래스에 새로움 품목을 저장하도록 요청
        Long savedProductId = productManager.addProduct(newProduct);

        // 3. 품목 저장에 대한 요청 반환
        return new ProductCreateResponse(savedProductId, "제품이 생성되었습니다.");
    }


    public List<ProductInfoResponse> getList() {
        return productReader.findAll().stream()
                .map(product -> {
                    return new ProductInfoResponse(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getDescription()
                    );
                })
                .collect(Collectors.toList());
    }
}
