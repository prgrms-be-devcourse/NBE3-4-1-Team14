package com.ll.cafeservice.domain.delivery.service;

import com.ll.cafeservice.entity.order.Order;
import com.ll.cafeservice.entity.order.OrderRepository;
import com.ll.cafeservice.entity.order.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 14 * * ?") // 2시->   초 분 시간 * * ? 포맷
    @Transactional
    public void shipping(){
        List<Order>orders = orderRepository.findByStatus(OrderStatus.WAITING);
        if(orders.isEmpty()){
            return;
        }
        for(Order order: orders){
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        }
    }
}
