package com.ll.cafeservice.domain.delivery.service;

import com.ll.cafeservice.entity.order.Order;
import com.ll.cafeservice.entity.order.OrderRepository;
import com.ll.cafeservice.entity.order.OrderStatus;
import com.ll.cafeservice.global.email.EmailService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 14 * * ?") // 2시->   초 분 시간 * * ? 포맷
    @Transactional
    public void shipping() {
        List<Order> orders = getWaitingOrders();
        if (orders.isEmpty()) {
            return;
        }

        Map<String, List<Order>> ordersGroupedByEmail = groupOrdersByEmail(orders);

        for (Map.Entry<String, List<Order>> entry : ordersGroupedByEmail.entrySet()) {
            String recipientEmail = entry.getKey();
            List<String> orderNumbers = extractOrderNumbers(entry.getValue());
            sendEmailToCustomer(recipientEmail, orderNumbers);
            updateOrderStatusToCompleted(entry.getValue());
        }
    }

    private List<Order> getWaitingOrders() {
        return orderRepository.findByStatus(OrderStatus.WAITING);
    }

    private Map<String, List<Order>> groupOrdersByEmail(List<Order> orders) {
        return orders.stream()
            .collect(Collectors.groupingBy(Order::getEmail));
    }

    private List<String> extractOrderNumbers(List<Order> orders) {
        return orders.stream()
            .map(order -> order.getOrderUuid().toString())
            .collect(Collectors.toList());
    }

    private void sendEmailToCustomer(String recipientEmail, List<String> orderNumbers) {
        emailService.sendOrderEmail(recipientEmail, orderNumbers);
    }

    private void updateOrderStatusToCompleted(List<Order> orders) {
        orders.forEach(order -> {
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        });
    }
}

