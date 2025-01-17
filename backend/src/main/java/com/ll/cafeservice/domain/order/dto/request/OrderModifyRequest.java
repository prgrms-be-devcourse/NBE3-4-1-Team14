package com.ll.cafeservice.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderModifyRequest(
        @NotBlank(message = "이메일은 필수 입니다.")
        String email,

        String address,

        @NotEmpty(message = "주문 항목은 비어 있을 수 없습니다.")
        List<OrderItemRequest> orderItems
) {

}
