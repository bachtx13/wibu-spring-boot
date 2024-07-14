package com.bachtx.mangaservice.controllers;

import com.bachtx.wibucommon.dtos.response.ErrorResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import com.bachtx.wibucommon.exceptions.RecordNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class _RestControllerAdvice {
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
    @ExceptionHandler({
            RecordNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleRecordNotFoundException(
            RecordNotFoundException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "request"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

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

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseTemplate<?> handleError2(HttpServletRequest req, Exception ex) {

        String msg = ex.getMessage();
        if (ex.getCause().getCause() instanceof SQLException e) {
            String KEY = "Key ";
            if (e.getMessage().contains(KEY)) {
                msg = e.getMessage().substring(e.getMessage().indexOf(KEY) + KEY.length());
            }
        }
        return ResponseTemplate.builder()
                .message(msg)
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
