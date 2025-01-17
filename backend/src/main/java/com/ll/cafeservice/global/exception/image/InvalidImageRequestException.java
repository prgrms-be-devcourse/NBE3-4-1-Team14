package com.ll.cafeservice.global.exception.image;

/**
 * 프론트에서 이미지 관련 잘못된 요청을 하는 경우 반환하는 예외
 */
public class InvalidImageRequestException extends RuntimeException {
  public InvalidImageRequestException(String message) {
    super(message);
  }
}
