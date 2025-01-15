package com.ll.cafeservice.domain.order.dto.response;

import com.ll.cafeservice.domain.order.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

import java.util.List;

public record OrderResponse (
        @Schema(description = "주문 ID")
    @NonNull
    Long id,

    @Schema(description = "주문자 이메일")
    @NonNull
    String email,

    @Schema(description = "주문 품목")
    @NonNull
    List<OrderItem>items // 배송된 품목 리스트
) {}
