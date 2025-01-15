package com.ll.cafeservice.domain.delivery.dto.response;

import com.ll.cafeservice.domain.order.OrderItem;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

public record DeliveryResponse (

        @NonNull
        String email,

        @NonNull
        LocalDate deliveryDate,

        @NonNull
        List<OrderItem> items // 배송된 품목 리스트
){ }
