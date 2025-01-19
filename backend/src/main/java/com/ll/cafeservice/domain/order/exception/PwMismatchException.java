package com.ll.cafeservice.domain.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "비밀번호가 일치하지 않습니다.")
public class PwMismatchException extends RuntimeException {
    public PwMismatchException(String message) {
        super(message);
    }
}
