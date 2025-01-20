package com.ll.cafeservice.domain.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record OrderCreateResponse (
        @Schema(description = "발급된 주문번호")
        UUID uuid
) { }
