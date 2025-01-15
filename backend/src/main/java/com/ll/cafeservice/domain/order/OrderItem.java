package com.ll.cafeservice.domain.order;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

@Schema(description = "아이템 정보")
public record OrderItem (
    @Schema(description = "상품 아이디")
    @NonNull
    Long productId,

    @Schema(description = "상품 개수")
    @NonNull
    Integer quantity
){ }
