package com.ll.cafeservice.domain.order.dto.request;

import com.ll.cafeservice.entity.order.OrderItem;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderRequest (
/*
        @NotEmpty
        List<OrderItem> items, // 여러 품목을 포함하는 리스트

        @NotEmpty
        String customerEmail,

        @NotEmpty
        String address
*/
        @NotBlank(message = "이메일은 필수 입니다.")
        String email,

        @NotBlank(message = "주소는 필수 입니다.")
        String address,

        @NotBlank
        List<OrderItemRequest>orderItems

){}