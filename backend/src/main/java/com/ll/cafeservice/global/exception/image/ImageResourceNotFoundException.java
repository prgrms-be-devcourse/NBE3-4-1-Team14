package com.ll.cafeservice.global.exception.image;

/**
 * 요청한 리소스가 존재하지 않는 경우에 발생하는 예외
 */
public class ImageResourceNotFoundException extends RuntimeException {
    public ImageResourceNotFoundException(String message) {
        super(message);
    }
}
