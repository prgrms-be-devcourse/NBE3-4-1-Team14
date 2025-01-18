package com.ll.cafeservice.domain.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "재고가 부족합니다.")
public class InSufficientStockException extends RuntimeException {
    public InSufficientStockException(String message) {
        super(message);
    }
}
