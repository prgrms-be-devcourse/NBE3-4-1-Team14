package com.ll.cafeservice.domain.order.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderDeleteRequest(
        @NotNull
        @Max(value=9999,message = "비밀번호는 필수 입니다.")
        int pw,

        @NotNull(message = "주문 번호는 필수 입니다.")
        UUID orderUuid
) {
}
