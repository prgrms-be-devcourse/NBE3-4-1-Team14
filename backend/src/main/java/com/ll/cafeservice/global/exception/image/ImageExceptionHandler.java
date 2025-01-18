package com.ll.cafeservice.global.exception.image;

import com.ll.cafeservice.api.Empty;
import com.ll.cafeservice.api.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ImageResourceNotFoundException.class)
    public Result<Empty> handleResourceNotFoundException(ImageResourceNotFoundException e) {
        return Result.error(404, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ImageStoreException.class)
    public Result<Empty> handleImageStoreException(ImageStoreException e) {
        return Result.error(500, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidImageRequestException.class)
    public Result<Empty> handleInvalidImageRequestException(InvalidImageRequestException e) {
        return Result.error(400, e.getMessage());
    }
}
