package com.ll.cafeservice.domain.product.service;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.domain.product.dto.request.ProductCreateRequest;
import com.ll.cafeservice.domain.product.dto.request.ProductUpdateRequest;
import com.ll.cafeservice.domain.product.dto.response.*;
import com.ll.cafeservice.domain.product.implement.ProductImageManager;
import com.ll.cafeservice.domain.product.implement.ProductManager;
import com.ll.cafeservice.domain.product.implement.ProductReader;
import com.ll.cafeservice.domain.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductManager productManager;
    private final ProductReader productReader;
    private final ProductImageManager productImageManager;
    private final ProductMapper productMapper;

    private Product getProduct(Long id) { //조회 메서드
        return productReader.findById(id);
    }

    public ProductCreateResponse addProduct(final ProductCreateRequest request) {

        // log.info("{} {} {} {}", request.name(), request.price(), request.description(), request.quantity());

        // 품목 이미지 파일 저장
        String filename = productImageManager.storeProductImage(request.image());

        //Request 정보를 이용하여 새로운 품목 (NewProduct 클래스)를 생성
        NewProduct newProduct = new NewProduct(
                request.name(),
                request.price(),
                request.description(),
                request.quantity(),
                filename
        );

        //ProductManager 클래스에 새로움 품목을 저장하도록 요청
        Long savedProductId = productManager.addProduct(newProduct);

        //품목 저장에 대한 요청 반환
        return new ProductCreateResponse(savedProductId, ResponseMessages.PRODUCT_CREATED);
    }


    public List<ProductInfoResponse> getList() {
        return productReader.findAllActivateProduct().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest request) {

        //제품 데이터 조회
        Product getProduct = getProduct(id);

        // 기존 이미지 삭제 및 새로운 이미지 저장
        productImageManager.deleteProductImageByFilename(getProduct.getImgFilename());
        String newImageFilename = productImageManager.storeProductImage(request.image());

        //요청 데이터를 이용하여 기존 제품 정보 수정
        Product updatedProduct = getProduct.updateProduct(
                request.name(),
                request.price(),
                request.description(),
                request.quantity(),
                newImageFilename
        );

        //수정된 제품 저장
        productManager.updatedProduct(updatedProduct);

        return new ProductUpdateResponse(id, ResponseMessages.PRODUCT_UPDATED);
    }


    public ProductDeleteResponse deleteProduct(Long id) {
        // 25.01.17 - 주문 정보가 삭제가 아닌 비활성화가 됨에 따라, 삭제되던 기능도 주석처리
        // productImageManager.deleteProductImageByFilename(existingProduct.getImgFilename());

        // 제품 비활성화
        productManager.deactivateProduct(getProduct(id));

        // 삭제 완료 응답
        return new ProductDeleteResponse(id, ResponseMessages.PRODUCT_DELETED);
    }
}