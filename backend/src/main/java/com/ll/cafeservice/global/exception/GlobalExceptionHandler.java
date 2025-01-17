package com.ll.cafeservice.global.exception;

import com.ll.cafeservice.api.Empty;
import com.ll.cafeservice.api.Result;
import java.nio.file.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Result<Empty> handleAccessDeniedException(AccessDeniedException e) {
        return Result.error(403, "접근 권한이 없습니다.");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
    @ExceptionHandler(InvalidCredentialsException.class)
    public Result<Empty> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return Result.error(401, "로그인 정보가 올바르지 않습니다.");
    }

}
