package com.ll.cafeservice.domain.order.controller.v1;

import com.ll.cafeservice.api.Empty;
import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.order.dto.request.OrderRequest;
import com.ll.cafeservice.domain.order.dto.response.OrderDeleteResponse;
import com.ll.cafeservice.domain.order.dto.response.OrderResponse;
import com.ll.cafeservice.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OrderControllerV1", description = "API 주문요청 컨트롤러")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderControllerV1 {

    private final OrderService orderService;
    @PostMapping
    @Operation(summary = "주문 요청", description = "주문 요청이 데이터베이스에 저장됩니다.")
    public Result<OrderResponse> order(@RequestBody @Valid OrderRequest request){
        log.info("주문 요청이 왔습니다.");
        return Result.success(orderService.order(request));
    }

    @GetMapping
    @Operation(summary = "주문 확인", description = "주문 요청을 확인")
    public Result<List<OrderResponse>>getOrder(@RequestParam String email){
        log.info("주문확인요청이 왔습니다.");
        List<OrderResponse> orderResponse = orderService.getOrdersByEmail(email);
        return Result.success(orderResponse);
    }
    @DeleteMapping
    @Operation(summary = "주문 삭제", description = "주문을 삭제합니다")
    public Result<OrderDeleteResponse>deleteOrder(@RequestParam String email){
        log.info("주문삭제요청이 왔습니다.");
        orderService.deleteOrder(email);
        return Result.success(new OrderDeleteResponse("주문이 성공적으로 취소되었습니다."));
    }
}
