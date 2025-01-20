package com.ll.cafeservice.domain.order.service;

import com.ll.cafeservice.domain.order.dto.request.OrderDeleteRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderItemRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderModifyRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderRequest;
import com.ll.cafeservice.domain.order.dto.response.OrderCreateResponse;
import com.ll.cafeservice.domain.order.dto.response.OrderItemResponse;
import com.ll.cafeservice.domain.order.dto.response.OrderModifyResponse;
import com.ll.cafeservice.domain.order.dto.response.OrderResponse;
import com.ll.cafeservice.domain.order.exception.InSufficientStockException;
import com.ll.cafeservice.domain.order.exception.OrderClosedException;
import com.ll.cafeservice.domain.order.exception.PwMismatchException;
import com.ll.cafeservice.entity.order.*;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderCreateResponse order(OrderRequest request){
        Order order = Order.builder().email(request.email()).address(request.address()).pw(request.pw()).orderDateTime(LocalDateTime.now()).status(OrderStatus.WAITING).orderUuid(UUID.randomUUID()).build();
        List<OrderItem>orderItems = createOrderItems(request,order);
        order.setOrderItems(orderItems);

        long totalPrice = calculateTotalPrice(orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return new OrderCreateResponse(order.getOrderUuid());
    }

    @Transactional
    public OrderModifyResponse modifyOrder(OrderModifyRequest modifyRequest){
        Order order = orderRepository.findByOrderUuid(modifyRequest.orderUuid());
        if(order.getStatus()!=OrderStatus.WAITING){
            throw new OrderClosedException("이미 처리가 완료된 주문입니다.");
        }
        if(modifyRequest.pw()!=order.getPw()){
            throw new PwMismatchException("비밀번호가 일치하지 않습니다.");
        }
        order.setAddress(modifyRequest.address());
        order.setOrderDateTime(LocalDateTime.now());
        return new OrderModifyResponse("주소 변경 완료");

    }

    //Orderuuid로 주문 목록 조회
    public List<OrderResponse>getOrders(){
        List<Order>orders = orderRepository.findAll();
        List<OrderResponse>response = new ArrayList<>();
        for(Order order : orders){
            response.add(
                    new OrderResponse(
                            order.getId(),
                            order.getEmail(),
                            createOrderItemResponses(order.getOrderItems()),
                            order.getOrderUuid(),
                            order.getStatus(),
                            order.getAddress(),
                            order.getTotalPrice(),
                            order.getOrderDateTime()));
        }
        return response;
    }

    public OrderResponse getOrderByOrderUuid(UUID orderUuid){
        Order order = orderRepository.findByOrderUuid(orderUuid);
        return new OrderResponse(
                order.getId(),
                order.getEmail(),
                createOrderItemResponses(order.getOrderItems()),
                order.getOrderUuid(),
                order.getStatus(),
                order.getAddress(),
                order.getTotalPrice(),
                order.getOrderDateTime());
    }

    //상품품목하나에대한 생성
    public OrderItem createOrderItem(Order order,ProductDetail productDetail,OrderItemRequest orderItemRequest){
        OrderItem orderItem = OrderItem.builder().order(order).product(productDetail).quantity(orderItemRequest.quantity()).build();
        orderItem.calculateTotalPrice();
        return orderItem;
    }

    //상품품목들list에생성
    public List<OrderItem>createOrderItems(OrderRequest request,Order order){
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest itemRequest : request.orderItems()){
            ProductDetail product = getProduct(itemRequest.productId());
            if(product.getQuantity()<itemRequest.quantity()){
                throw new InSufficientStockException("재고가 부족합니다.");
            }
            OrderItem orderItem = createOrderItem(order,product,itemRequest);
            order.addOrderItem(orderItem);
            orderItems.add(orderItem);
            product.updateQuantity(itemRequest.quantity());
        }
        return orderItems;
    }

    @Transactional
    public void deleteOrder(OrderDeleteRequest orderDeleteRequest){
        Order order = orderRepository.findByOrderUuid(orderDeleteRequest.orderUuid());
        if(order.getStatus()!=OrderStatus.WAITING){
            throw new OrderClosedException("이미 처리가 완료된 주문입니다.");
        }
        if(orderDeleteRequest.pw()!=order.getPw()){
            throw new PwMismatchException("비밀번호가 일치하지 않습니다.");
        }
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    private long calculateTotalPrice(List<OrderItem> orderItems){
        long totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += (orderItem.getPrice() * orderItem.getQuantity());
        }
        return totalPrice;
    }
    private ProductDetail getProduct(Long productId){
        return productDetailRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("상품존재안함"));
    }

    private List<OrderItemResponse> createOrderItemResponses(List<OrderItem> orderItems){
        List<OrderItemResponse>orderItemResponses = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            orderItemResponses.add(
                    new OrderItemResponse(
                            orderItem.getId(),
                            orderItem.getProduct().getName(),
                            orderItem.getQuantity(),
                            orderItem.getPrice()
                    )
            );
        }
        return orderItemResponses;
    }
}
