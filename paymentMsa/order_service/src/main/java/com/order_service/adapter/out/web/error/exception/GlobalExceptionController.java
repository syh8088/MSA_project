package com.order_service.adapter.out.web.error.exception;

import com.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    public ApiResponse<?> handleBaseException(BusinessException e) {

        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), e.getErrorCode());
    }
}
