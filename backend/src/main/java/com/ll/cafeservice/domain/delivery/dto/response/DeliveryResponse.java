//package com.ll.cafeservice.domain.delivery.dto.response;
//
//import com.ll.cafeservice.entity.order.OrderItem;
//import jakarta.persistence.Column;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import org.springframework.lang.NonNull;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//public record DeliveryResponse (
//
//        @NonNull
//        String email,
//
//        @NonNull
//        LocalDate deliveryDate,
////
//        Long orderId,
//
//        @NonNull
//        List<OrderItem> items, // 배송된 품목 리스트
//
//        UUID uuid
//){ }
