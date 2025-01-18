package com.ll.cafeservice.global.exception;

import com.ll.cafeservice.api.Empty;
import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.security.exception.CustomJwtException;
import com.ll.cafeservice.security.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public Result<Empty> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return Result.error(401, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomJwtException.class)
    public  Result<Empty> handleCustomJwtException(CustomJwtException ex) {
        return Result.error(401, ex.getMessage());
    }


}
