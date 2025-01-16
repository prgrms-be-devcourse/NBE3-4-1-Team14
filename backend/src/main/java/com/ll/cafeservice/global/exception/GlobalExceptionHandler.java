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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Result<Empty> handleResourceNotFoundException(ResourceNotFoundException e) {
        return Result.error(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ImageStoreException.class)
    public Result<Empty> handleImageStoreException(ImageStoreException e) {
        return Result.error(500, e.getMessage());
    }
}
