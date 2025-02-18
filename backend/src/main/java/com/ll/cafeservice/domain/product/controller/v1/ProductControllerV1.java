package com.ll.cafeservice.domain.product.controller.v1;

import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.product.dto.request.ProductCreateRequest;
import com.ll.cafeservice.domain.product.dto.request.ProductUpdateRequest;
import com.ll.cafeservice.domain.product.dto.response.ProductCreateResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductDeleteResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductInfoResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductUpdateResponse;
import com.ll.cafeservice.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductControllerV1", description = "API 품목관리 컨트롤러")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductControllerV1 {

    private final ProductService productService;

    // 품목 추가
    @Operation(
        summary = "품목 추가",
        description = "품목을 등록합니다. 등록 시에 이미지 파일은 .png를 권장드립니다.")
    @PostMapping("/api/v1/admin/product")
    public Result<ProductCreateResponse> createProduct(
        @ModelAttribute("request") ProductCreateRequest request
    ) {
        ProductCreateResponse response = productService.addProduct(request);
        return Result.success(response);
    }

    // 품목 수정
    @Operation(
        summary = "품목 수정",
        description = "품목을 수정합니다. 등록 시와 마찬가지의 데이터를 넘겨받습니다.")
    @PutMapping("/api/v1/admin/product/{id}")
    public Result<ProductUpdateResponse> updateProduct(
        @PathVariable Long id,
        @ModelAttribute("request") ProductUpdateRequest request
    ) {
        ProductUpdateResponse response = productService.updateProduct(id, request);
        return Result.success(response);

    }

    // 품목 리스트 반환
    @Operation(
        summary = "품목 리스트 반환",
        description = "품목의 리스트를 전달받습니다. 현재 활성화된 품목 전체를 반환합니다.")
    @GetMapping("/api/v1/product/list")
    public Result<List<ProductInfoResponse>> list(
    ) {
        List<ProductInfoResponse> response = productService.getList();
        return Result.success(response);
    }

    // 품목 삭제
    @Operation(
        summary = "품목 삭제",
        description = "품목을 삭제합니다. 실제로는 내부적으로 비활성화됩니다.")
    @DeleteMapping("/api/v1/admin/product/{id}")
    public Result<ProductDeleteResponse> deleteProduct(
        @PathVariable Long id
    ) {
        ProductDeleteResponse response = productService.deleteProduct(id);
        return Result.success(response);
    }
}
