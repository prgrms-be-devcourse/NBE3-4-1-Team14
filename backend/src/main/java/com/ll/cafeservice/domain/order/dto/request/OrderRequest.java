package com.ll.cafeservice.domain.order.dto.request;

import com.ll.cafeservice.entity.order.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest (

        @NotBlank(message = "이메일은 필수 입니다.")
        String email,

        @NotBlank(message = "주소는 필수 입니다.")
        String address,

        @NotEmpty(message = "주문 항목은 비어 있을 수 없습니다.")
        List<OrderItemRequest>orderItems

){}