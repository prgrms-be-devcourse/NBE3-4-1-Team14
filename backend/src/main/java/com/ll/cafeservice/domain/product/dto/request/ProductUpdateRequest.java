package com.ll.cafeservice.domain.product.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;


public record ProductUpdateRequest(

        @NotEmpty
        String name,

        @NotEmpty
        Integer price,

        @NotEmpty
        String description,

        @NotEmpty
        Integer quantity,

        @NotEmpty
        MultipartFile image
) { }
