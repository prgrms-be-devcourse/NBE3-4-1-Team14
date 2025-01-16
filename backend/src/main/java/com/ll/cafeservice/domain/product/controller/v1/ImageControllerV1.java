package com.ll.cafeservice.domain.product.controller.v1;

import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.product.implement.ProductImageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageControllerV1 {

    private final ProductImageManager productImageManager;

    @GetMapping("/{url}")
    public Result<MultipartFile> getImage(
            @PathVariable String url) {
        return Result.success(productImageManager.getProductImageByUrl(url));
    }
}
