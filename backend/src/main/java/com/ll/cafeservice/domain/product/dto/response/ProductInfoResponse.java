package com.ll.cafeservice.domain.product.dto.response;

import org.springframework.lang.NonNull;

public record ProductInfoResponse (

        @NonNull
        Long id,

        @NonNull
        String name,

        @NonNull
        Integer price,

        @NonNull
        String description
){ }
