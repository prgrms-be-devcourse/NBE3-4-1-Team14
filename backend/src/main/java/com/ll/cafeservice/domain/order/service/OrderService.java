package com.ll.cafeservice.domain.order.service;

import com.ll.cafeservice.domain.order.dto.request.OrderItemRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderRequest;
import com.ll.cafeservice.domain.order.dto.response.OrderResponse;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.entity.order.*;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

        List<OrderItem>orderItems = createOrderItems(request,order);
        order.setOrderItems(orderItems);

        double totalPrice = calculateTotalPrice(orderItems);
        //order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return new OrderResponse(order.getId(),order.getEmail(),orderItems);
    }


    //email로 주문 목록 조회
    public List<OrderResponse>getOrdersByEmail(String email){
        List<Order>orders= orderRepository.findByEmail(email);
        List<OrderResponse>response = new ArrayList<>();
        for(Order order : orders){
            List<OrderItem>orderItems = orderItemRepository.findByOrderId(order.getId());
            response.add(new OrderResponse(order.getId(),order.getEmail(),orderItems));
        }
        return response;
    }

    //상품품목하나에대한 생성
    public OrderItem createOrderItem(Order order,ProductDetail productDetail,OrderItemRequest orderItemRequest){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(productDetail);
        orderItem.setQuantity(orderItemRequest.quantity());
        orderItem.calculateTotalPrice();
        orderItem.setOrderDateTime(LocalDateTime.now());
        OrderStatus orderStatus = alterOrderStatus(orderItem);
        orderItem.setStatus(orderStatus);
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
            //orderItemRepository.save(orderItem);
        }
        return orderItems;
    }
    public void deleteOrder(String email){
        List<Order> orders = orderRepository.findByEmail(email);
        if(orders.isEmpty()){
            throw new IllegalArgumentException("삭제 가능한 주문이 없습니다.");
        }
        for(Order order : orders){
            for(OrderItem orderItem : order.getOrderItems()){
                orderItem.setStatus(OrderStatus.CANCELED);
            }
            orderRepository.save(order);
        }
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

    public OrderStatus alterOrderStatus(OrderItem orderItem){
        LocalDateTime standardTime = LocalDateTime.now().minusDays(1).withHour(14).withMinute(0).withSecond(0).withNano(0);
        if(orderItem.getOrderDateTime().isBefore(standardTime)){
            return OrderStatus.COMPLETED;
        }else{
            return OrderStatus.WAITING;
        }
    }

    // 주문 하루넘은거 처리하기 - status 변경하기 /
    // 주문 수정하기
    // 주문 삭제하기
    // 예외처리 : 해당 product 없을때 , 수량 부족할때
    // 페이징
}
