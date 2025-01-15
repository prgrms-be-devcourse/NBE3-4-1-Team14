package com.ll.cafeservice.domain.order.controller.v1;

import com.ll.cafeservice.api.Empty;
import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.order.dto.request.OrderRequest;
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

    /**
     *  주문요청 비즈니스 로직 처리후, 처리결과를 반환
     * @param request 주문요청 정보
     * @return 주문요청 결과
     */
    @PostMapping
    @Operation(summary = "주문 요청", description = "주문 요청이 데이터베이스에 저장됩니다.")
    public Result<OrderResponse> order(
        @RequestBody @Valid OrderRequest request
    ){
        log.info("주문 요청이 왔습니다.");
        return Result.success(orderService.order(request));
    }

    // 이메일에 해당하는 주문 내역을 확인한다.
    @GetMapping("/{email}")
    public Result<List<OrderResponse>> list(
            @PathVariable String email
    ){
        return Result.success(orderService.getList(email));
    }
}
