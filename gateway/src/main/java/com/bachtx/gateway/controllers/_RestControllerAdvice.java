package com.bachtx.gateway.controllers;

import com.bachtx.wibucommon.dtos.response.ErrorResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class _RestControllerAdvice {
    @ExceptionHandler({
            NoResourceFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseTemplate<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "gateway"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseTemplate<?> handleNoNotFoundException(
            NotFoundException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "gateway"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.ERROR)
                .build();
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "request"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.ERROR)
                .build();
    }

    private ErrorResponse _extractErrorFromException(Exception ex, @Nullable String target) {
        String detailErrorMessage = Objects.isNull(ex.getCause()) ?
                ex.getMessage() :
                ex.getCause().getMessage();
        return ErrorResponse.builder()
                .detail(detailErrorMessage)
                .target(target)
                .build();
    }
}
