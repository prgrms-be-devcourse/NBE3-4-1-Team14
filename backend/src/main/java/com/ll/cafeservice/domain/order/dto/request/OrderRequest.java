package com.ll.cafeservice.domain.order.dto.request;

import com.ll.cafeservice.entity.order.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRequest (

        @NotBlank(message = "이메일은 필수 입니다.")
        String email,

        @NotBlank(message = "주소는 필수 입니다.")
        String address,

        @NotEmpty(message = "주문 항목은 비어 있을 수 없습니다.")
        List<OrderItemRequest>orderItems,

        @NotNull
        @Max(value=9999,message = "비밀번호는 필수 입니다.")
        int pw,

        @Schema(description = "상품 주문시간")
        @NonNull
        LocalDateTime orderDateTime
){}