package com.ll.cafeservice.domain.product.dto.response;

import lombok.Builder;
import org.springframework.lang.NonNull;

@Builder
public record ProductInfoResponse (

        @NonNull
        Long id,

        @NonNull
        String name,

        @NonNull
        Integer price,

        @NonNull
        String description,

        @NonNull
        String filename
){ }
