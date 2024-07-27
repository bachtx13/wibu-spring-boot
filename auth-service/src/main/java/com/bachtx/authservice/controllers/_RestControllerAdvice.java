package com.bachtx.authservice.controllers;

import com.bachtx.authservice.exceptions.EmailAlreadyExistsException;
import com.bachtx.authservice.exceptions.RegistrationVerifyErrorException;
import com.bachtx.authservice.exceptions.UnverifiedUserException;
import com.bachtx.authservice.exceptions.UserNotFoundException;
import com.bachtx.wibucommon.dtos.response.ErrorResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import com.bachtx.wibucommon.exceptions.ServiceErrorException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Objects;

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

    @ExceptionHandler({
            EmailAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleEmailAlreadyExistsException(RuntimeException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "email"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            UnverifiedUserException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleUnverifiedUser(UnverifiedUserException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "user"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            RegistrationVerifyErrorException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleRegistrationVerifyErrorException(RuntimeException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "registration"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            UserNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseTemplate<?> handleUserNotFoundException(RuntimeException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, "login"))
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

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

    private ErrorResponse _extractErrorFromException(Exception ex, @Nullable String target){
        String detailErrorMessage = Objects.isNull(ex.getCause()) ?
                ex.getMessage() :
                ex.getCause().getMessage();
        return ErrorResponse.builder()
                .detail(detailErrorMessage)
                .target(target)
                .build();
    }
}
