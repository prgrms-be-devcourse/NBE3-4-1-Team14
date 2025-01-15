package com.ll.cafeservice.domain.order.dto.request;

import com.ll.cafeservice.domain.order.OrderItem;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest (

        @NotEmpty
        List<OrderItem> items, // 여러 품목을 포함하는 리스트

        @NotEmpty
        String customerEmail,

        @NotEmpty
        String address
){ }