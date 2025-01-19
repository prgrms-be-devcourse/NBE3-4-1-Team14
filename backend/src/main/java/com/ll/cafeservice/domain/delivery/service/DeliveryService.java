package com.ll.cafeservice.domain.delivery.service;

//import com.ll.cafeservice.domain.delivery.Delivery;
//import com.ll.cafeservice.domain.delivery.dto.response.DeliveryResponse;
//import com.ll.cafeservice.domain.delivery.implement.DeliveryReader;
import com.ll.cafeservice.domain.order.dto.response.OrderResponse;
//import com.ll.cafeservice.entity.delivery.DeliveryDetail;
//import com.ll.cafeservice.entity.delivery.DeliveryDetailRepository;
import com.ll.cafeservice.entity.delivery.Shipped;
import com.ll.cafeservice.entity.delivery.ShippedRepository;
import com.ll.cafeservice.entity.order.Order;
import com.ll.cafeservice.entity.order.OrderRepository;
import com.ll.cafeservice.entity.order.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final ShippedRepository shippedRepository;

    @Scheduled(cron = "0 0 14 * * ?") // 2시
    //@Scheduled(cron = "0 24 23 * * ?") // 초 분 시간 * * ? 포맷 - 23시 24분.
    public void shipping(){
        List<Order>orders = orderRepository.findByStatus(OrderStatus.WAITING);
        if(orders.isEmpty()){
            return;
        }
        for(Order order: orders){
            UUID orderUuid = order.getOrderUuid();
            Shipped shipped = Shipped.builder().uuid(orderUuid).build();
            shippedRepository.save(shipped);
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        }
    }
}
