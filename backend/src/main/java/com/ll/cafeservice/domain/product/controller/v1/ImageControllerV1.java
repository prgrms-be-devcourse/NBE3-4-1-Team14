package com.ll.cafeservice.domain.product.controller.v1;

import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.product.implement.ProductImageManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ImageControllerV1", description = "API 품목 이미지 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageControllerV1 {

    private final ProductImageManager productImageManager;

    @GetMapping("/{filename}")
    public Result<Resource> getImage(
            @PathVariable String filename) {
        return Result.success(productImageManager.getProductImageByFilename(filename));
    }

    @GetMapping("/path/{filename}")
    public ResponseEntity<Resource> getImageFileName(@PathVariable String filename) {
        try {
            // 이미지 Resource 가져오기
            Resource resource = productImageManager.getProductImageByFilename(filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG) // 또는 적절한 미디어 타입
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * 테스트용 API
     * @return 전달한 이미지 파일을 저장후, 원본 파일을 다시 전달한다.
     */
    @PostMapping("/test")
    public Result<Resource> test(
            @ModelAttribute MultipartFile file
    ){
        String filename = productImageManager.storeProductImage(file);
        return Result.success(productImageManager.getProductImageByFilename(filename));
    }
}
