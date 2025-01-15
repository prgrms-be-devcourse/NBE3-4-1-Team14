package com.ll.cafeservice.global.exception;

import com.ll.cafeservice.api.Empty;
import com.ll.cafeservice.api.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Result<Empty> handleAccessDeniedException(AccessDeniedException e) {
        return Result.error(403, "접근 권한이 없습니다.");
    }


}
