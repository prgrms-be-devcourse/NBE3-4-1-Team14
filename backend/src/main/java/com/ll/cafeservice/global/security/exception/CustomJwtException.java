package com.ll.cafeservice.global.security.exception;

public class CustomJwtException extends RuntimeException {
    public CustomJwtException(String message) {
        super(message);
    }
}