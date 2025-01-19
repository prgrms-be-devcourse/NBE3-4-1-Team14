package com.ll.cafeservice.domain.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "이미 처리된 주문입니다.")
public class OrderClosedException extends RuntimeException {
  public OrderClosedException(String message) {
    super(message);
  }
}
