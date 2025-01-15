package com.ll.cafeservice.domain.product.service;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.domain.product.dto.request.ProductCreateRequest;
import com.ll.cafeservice.domain.product.dto.response.ProductCreateResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductInfoResponse;
import com.ll.cafeservice.domain.product.implement.ProductManager;
import com.ll.cafeservice.domain.product.implement.ProductReader;
import com.ll.cafeservice.domain.product.implement.ProductValidator;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductManager productManager;
    private ProductReader productReader;
    private ProductValidator productValidator;

    public ProductCreateResponse addProduct(final ProductCreateRequest request) {
        
        // 1. Request 정보를 이용하여 새로운 품목 (NewProduct 클래스)를 생성
        NewProduct newProduct = new NewProduct();

        // 2. ProductManager 클래스에 새로움 품목을 저장하도록 요청
        productManager.addProduct(newProduct);

        // 3. 품목 저장에 대한 요청 반환
        return new ProductCreateResponse(1L, "1");
    }

    public void func(){
        Product product = new Product();

    }

    public List<ProductInfoResponse> getList() {
        return productReader.findAll().stream()
                .map(product -> {
                    // 도메인 객체를 Response 형태로 변환
                    ProductInfoResponse response = new ProductInfoResponse(1L, "1", 1, "1");
                    return response;
                }).toList();
    }

}
