package com.ll.cafeservice.domain.order.dto.response;

public record OrderItemResponse(
        Long id,
        String name,
        int quantity,
        Long price
) { }
