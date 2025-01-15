package com.ll.cafeservice.domain.product.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ProductCreateRequest(

        @NotEmpty
        String name,

        @NotEmpty
        Integer price,

        @NotEmpty
        String description,

        @NotEmpty
        Integer quantity
) { }
