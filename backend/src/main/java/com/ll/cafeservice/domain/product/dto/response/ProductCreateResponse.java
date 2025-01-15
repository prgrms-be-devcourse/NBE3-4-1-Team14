package com.ll.cafeservice.domain.product.dto.response;

import org.springframework.lang.NonNull;

public record ProductCreateResponse (
        @NonNull
        Long id,

        @NonNull
        String message // 예: "제품이 성공적으로 생성되었습니다."
){ }
