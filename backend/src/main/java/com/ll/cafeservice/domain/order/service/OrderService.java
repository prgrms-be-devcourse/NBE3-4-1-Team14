package com.ll.cafeservice.domain.order.service;

import com.ll.cafeservice.domain.order.dto.request.OrderRequest;
import com.ll.cafeservice.domain.order.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    public OrderResponse order(OrderRequest request) {
        // TODO : 주문을 처리한다.

        return new OrderResponse(1L, "주문 성공", List.of());
    }

    public List<OrderResponse> getList(String email) {
        return List.of();
    }
}
