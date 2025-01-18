package com.ll.cafeservice.domain.order.service;

import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.order.dto.request.OrderDeleteRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderItemRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderModifyRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderRequest;
import com.ll.cafeservice.domain.order.dto.response.OrderModifyResponse;
import com.ll.cafeservice.domain.order.dto.response.OrderResponse;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.entity.order.*;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;
    private final OrderItemRepository orderItemRepository;


    public OrderResponse order(OrderRequest request){
        Order order = new Order();

        order.setEmail(request.email());
        order.setAddress(request.address());
        order.setPw(request.pw());
        order.setOrderDateTime(LocalDateTime.now());
        OrderStatus orderStatus = alterOrderStatus(order);
        order.setStatus(orderStatus);
        order.setOrderUuid(UUID.randomUUID());
        List<OrderItem>orderItems = createOrderItems(request,order);
        order.setOrderItems(orderItems);

        double totalPrice = calculateTotalPrice(orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return new OrderResponse(order.getId(),order.getEmail(),orderItems,order.getOrderUuid(),order.getStatus(),order.getTotalPrice());
    }

    public OrderModifyResponse modifyOrder(OrderModifyRequest modifyRequest){
        Order order = orderRepository.findByOrderUuid(modifyRequest.orderUuid());
        if(modifyRequest.pw()!=order.getPw()){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        order.setAddress(modifyRequest.address());
        order.setOrderDateTime(LocalDateTime.now());
        return new OrderModifyResponse("주소 변경 완료");

    }

    //Orderuuid로 주문 목록 조회
    public List<OrderResponse>getOrdersByOrderUuid(UUID orderUuid){
        Order order = orderRepository.findByOrderUuid(orderUuid);
        List<OrderResponse>response = new ArrayList<>();
        List<OrderItem>orderItems = orderItemRepository.findByOrderId(order.getId());
        response.add(new OrderResponse(order.getId(),order.getEmail(),orderItems,order.getOrderUuid(),order.getStatus(),order.getTotalPrice()));

        return response;
    }


    //상품품목하나에대한 생성
    public OrderItem createOrderItem(Order order,ProductDetail productDetail,OrderItemRequest orderItemRequest){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(productDetail);
        orderItem.setQuantity(orderItemRequest.quantity());
        orderItem.calculateTotalPrice();
        //OrderStatus orderStatus = alterOrderStatus(order);
        //orderItem.setStatus(orderStatus);
        return orderItem;
    }

    //상품품목들list에생성
    public List<OrderItem>createOrderItems(OrderRequest request,Order order){
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest itemRequest : request.orderItems()){
            ProductDetail product = getProduct(itemRequest.productId());
            OrderItem orderItem = createOrderItem(order,product,itemRequest);
            order.addOrderItem(orderItem);
            orderItems.add(orderItem);
        }
        return orderItems;
    }
    public void deleteOrder(OrderDeleteRequest orderDeleteRequest){
        Order order = orderRepository.findByOrderUuid(orderDeleteRequest.orderUuid());
        if(orderDeleteRequest.pw()!=order.getPw()){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    private double calculateTotalPrice(List<OrderItem> orderItems){
        double totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getPrice();
        }
        return totalPrice;
    }
    private ProductDetail getProduct(Long productId){
        Optional<ProductDetail> product = this.productDetailRepository.findById(productId);
        return product.orElseThrow(()->new IllegalArgumentException("상품존재안함"));
    }

    public OrderStatus alterOrderStatus(Order order){
        LocalDateTime standardTime = LocalDateTime.now().minusDays(1).withHour(14).withMinute(0).withSecond(0).withNano(0);
        if(order.getOrderDateTime().isBefore(standardTime)){
            return OrderStatus.COMPLETED;
        }
        return OrderStatus.WAITING;
    }

}
