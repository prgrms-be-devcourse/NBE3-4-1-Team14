package com.ll.cafeservice.domain.order.controller.v1;

import com.ll.cafeservice.api.Empty;
import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.order.dto.request.OrderCheckRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderDeleteRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderModifyRequest;
import com.ll.cafeservice.domain.order.dto.request.OrderRequest;
import com.ll.cafeservice.domain.order.dto.response.OrderCreateResponse;
import com.ll.cafeservice.domain.order.dto.response.OrderDeleteResponse;
import com.ll.cafeservice.domain.order.dto.response.OrderModifyResponse;
import com.ll.cafeservice.domain.order.dto.response.OrderResponse;
import com.ll.cafeservice.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "OrderControllerV1", description = "API 주문요청 컨트롤러")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderControllerV1 {

    private final OrderService orderService;
    @PostMapping
    @Operation(summary = "주문 요청", description = "주문 요청이 데이터베이스에 저장됩니다.")
    public Result<OrderCreateResponse> order(@RequestBody @Valid OrderRequest request){
        log.info("주문 요청이 왔습니다.");
        OrderCreateResponse orderResponse = orderService.order(request);
        return Result.success(orderResponse);
    }

    // uuid로 단건조회
    @GetMapping
    public Result<OrderResponse> getOrderbyUuid(
            @RequestParam UUID orderUuid,
            @RequestParam String password) {
        log.info("주문확인요청이 왔습니다.");
        OrderResponse orderResponse = orderService.getOrderByOrderUuid(orderUuid);
        return Result.success(orderResponse);
    }

    // 전체조회
    @GetMapping("/list")
    @Operation(summary = "관리자 전체 목록 확인")
    public Result<List<OrderResponse>>getOrderList(@RequestBody @Valid OrderCheckRequest orderCheckRequest){
        log.info("관리자 페이지 주문리스트 확인 요청이 왔습니다.");
        List<OrderResponse> orderResponse = orderService.getOrders();
        return Result.success(orderResponse);
    }

    @DeleteMapping
    @Operation(summary = "주문 삭제", description = "주문을 삭제합니다")
    public Result<OrderDeleteResponse>deleteOrder(@RequestBody @Valid OrderDeleteRequest orderDeleteRequest){
        log.info("주문삭제요청이 왔습니다.");
        orderService.deleteOrder(orderDeleteRequest);
        OrderDeleteResponse orderResponse = new OrderDeleteResponse("주문이성공적으로 취소되었습니다.");
        return Result.success(orderResponse);
    }

    @PutMapping
    @Operation(summary = "주문 수정",description = "주문을 수정합니다")
    public Result<OrderModifyResponse>modifyOrder(@RequestBody @Valid OrderModifyRequest orderModifyRequest){
        log.info("주문 수정요청이 왔습니다.");
        OrderModifyResponse orderResponse = orderService.modifyOrder(orderModifyRequest);
        return Result.success(orderResponse);
    }
}
