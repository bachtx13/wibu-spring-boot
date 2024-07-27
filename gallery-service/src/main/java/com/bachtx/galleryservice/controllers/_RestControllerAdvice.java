package com.bachtx.galleryservice.controllers;

import com.bachtx.galleryservice.exceptions.UploadFileException;
import com.bachtx.wibucommon.dtos.response.ErrorResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import com.bachtx.wibucommon.exceptions.ServiceErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class _RestControllerAdvice {
    @ExceptionHandler({
            ServiceErrorException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseTemplate<?> handleServiceErrorException(RuntimeException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "server"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.ERROR)
                .build();
    }

    @ExceptionHandler({
            UploadFileException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleUploadFileException(UploadFileException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "request"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "request"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.ERROR)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseTemplate<?> handleException(Exception ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, null))
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
