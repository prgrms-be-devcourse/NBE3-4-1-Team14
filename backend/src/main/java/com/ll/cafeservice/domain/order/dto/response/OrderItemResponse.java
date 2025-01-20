package com.ll.cafeservice.domain.order.dto.response;

public record OrderItemResponse(
        String name,
        int quantity,
        Long price
) { }
