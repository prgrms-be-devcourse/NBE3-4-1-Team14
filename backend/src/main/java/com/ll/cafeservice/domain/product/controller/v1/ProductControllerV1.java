package com.ll.cafeservice.domain.product.controller.v1;

import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.product.dto.request.ProductCreateRequest;
import com.ll.cafeservice.domain.product.dto.request.ProductUpdateRequest;
import com.ll.cafeservice.domain.product.dto.response.ProductCreateResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductDeleteResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductInfoResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductUpdateResponse;
import com.ll.cafeservice.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductControllerV1", description = "API 품목관리 컨트롤러")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductControllerV1 {

    private final ProductService productService;

    // 품목 추가
    @PostMapping
    public Result<ProductCreateResponse> createProduct(
            @ModelAttribute("request") ProductCreateRequest request
    ){
        return Result.success(productService.addProduct(request));
    }

    // 품목 수정
    @PutMapping("/{id}")
    public Result<ProductUpdateResponse> updateProduct(
            @PathVariable Long id,
            @ModelAttribute("request") ProductUpdateRequest request
    ) {
        ProductUpdateResponse response = productService.updatedProduct(id, request);
        return Result.success(response);

    }

    // 품목 리스트 반환
    // 필요 시, 페이징 기능을 넣어볼 수 있다.
    @GetMapping("/list")
    public Result<List<ProductInfoResponse>> list(
    ){
        return Result.success(productService.getList());
    }

    // 품목 삭제
    @DeleteMapping("/{id}")
    public Result<ProductDeleteResponse> deleteProduct(
            @PathVariable Long id
    ){
        productService.deleteProduct(id);
        ProductDeleteResponse productDeleteResponse = new ProductDeleteResponse(id, "d");
        return Result.success(productDeleteResponse);
    }
}
