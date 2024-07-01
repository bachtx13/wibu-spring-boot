package com.bachtx.authservice.controllers;

import com.bachtx.wibucommon.dtos.response.ErrorResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class _RestControllerAdvice {
    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseTemplate.<ErrorResponse>builder()
                .errors(
                        ex.getBindingResult()
                                .getAllErrors().stream()
                                .map(error -> {
                                    ConstraintViolationImpl<?> constraintViolation = error.unwrap(ConstraintViolationImpl.class);
                                    return ErrorResponse.builder()
                                            .target(constraintViolation.getPropertyPath().toString())
                                            .detail(
                                                    constraintViolation.getMessage()
                                            )
                                            .build();
                                })
                                .toList()
                )
                .message("Validation fail")
                .status(EResponseStatus.FAIL)
                .build();
    }
}
