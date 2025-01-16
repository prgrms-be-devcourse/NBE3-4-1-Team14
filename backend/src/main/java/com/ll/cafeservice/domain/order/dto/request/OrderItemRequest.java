package com.ll.cafeservice.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Schema(description = "아이템 정보")
public record OrderItemRequest (

        @Schema(description = "상품 아이디")
    @NonNull
    Long productId,

        @Schema(description = "상품 개수")
    @NonNull
    Integer quantity,

        @Schema(description = "상품 주문시간")
    @NonNull
    LocalDateTime orderDateTime

) { }
