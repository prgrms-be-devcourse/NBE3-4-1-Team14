package com.ll.cafeservice.domain.order.dto.response;

import com.ll.cafeservice.entity.order.OrderItem;
import com.ll.cafeservice.entity.order.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.cglib.core.Local;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record OrderResponse (
        @Schema(description = "주문 ID")
        @NonNull
        Long id,

        @Schema(description = "주문자 이메일")
        @NonNull
        String email,

        @Schema(description = "주문 품목")
        @NonNull
        List<OrderItem>items, // 주문한 품목 리스트

        @Schema(description = "발급된 주문번호")
        UUID uuid,

        @Schema(description = "주문 상태")
        OrderStatus orderStatus,

        @Schema(description = "주소")
        String address,

        @Schema(description = "주문 총액")
        long totalPrice
) {}
