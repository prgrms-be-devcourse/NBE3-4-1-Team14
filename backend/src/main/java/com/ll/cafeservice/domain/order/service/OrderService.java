package com.ll.cafeservice.domain.order.service;

import com.ll.cafeservice.domain.order.dto.request.OrderItemRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderRequest;
import com.ll.cafeservice.domain.order.dto.response.OrderResponse;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.entity.order.Order;
import com.ll.cafeservice.entity.order.OrderItem;
import com.ll.cafeservice.entity.order.OrderItemRepository;
import com.ll.cafeservice.entity.order.OrderRepository;
import com.ll.cafeservice.entity.product.product.ProductDetail;
import com.ll.cafeservice.entity.product.product.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;
    private final OrderItemRepository orderItemRepository;

    // 주문처리
    public Order createOrder(OrderRequest request){
        Order order = new Order();
        order.setEmail(request.email());
        order.setAddress(request.address());
        return orderRepository.save(order);
    }

    public OrderResponse order(OrderRequest request){
        Order order = createOrder(request);
        List<OrderItem>orderItems = createOrderItems(request,order);
        double totalPrice = calculateTotalPrice(orderItems);
        order.setTotalPrice(totalPrice);
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
        orderItem.setOrderId(order.getId());
        orderItem.setProduct(productDetail);
        orderItem.setQuantity(orderItemRequest.quantity());
        orderItem.calculateTotalPrice();
        return orderItem;
    }

    //상품품목들생성
    public List<OrderItem>createOrderItems(OrderRequest request,Order order){
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest itemRequest : request.orderItems()){
            ProductDetail product = getProduct(itemRequest.productId());
            OrderItem orderItem = createOrderItem(order,product,itemRequest);
            orderItems.add(orderItem);
        }
        return orderItems;
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
        return product.orElseThrow(()->new IllegalArgumentException("Error!"));
    }

    // 주문 하루넘은거 처리하기 - status 변경하기 / delivery로 넘기기


    //private double calculateTotalPrice(List<OrderItemRequest>)
    /*
    public OrderResponse order(OrderRequest request) {
        // TODO : 주문을 처리한다.

        return new OrderResponse(1L, "주문 성공", List.of());
    }

    public List<OrderResponse> getList(String email) {
        return List.of();
    }*/
}
