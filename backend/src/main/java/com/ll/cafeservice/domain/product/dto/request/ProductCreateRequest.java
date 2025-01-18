package com.ll.cafeservice.domain.product.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public record ProductCreateRequest(

        @NotEmpty
        String name,

        @NotEmpty
        Integer price,

        @NotEmpty
        String description,

        @NotEmpty
        Integer quantity,

        MultipartFile image// 이미지 필드 추가) { }
) { }
