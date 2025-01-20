package com.ll.cafeservice.domain.order.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record OrderModifyRequest(
        String address,

        @NotNull
        @Max(value=9999,message = "비밀번호는 필수 입니다.")
        int pw,

        @NotNull(message = "주문 번호는 필수 입니다.")
        UUID orderUuid
) {
}
