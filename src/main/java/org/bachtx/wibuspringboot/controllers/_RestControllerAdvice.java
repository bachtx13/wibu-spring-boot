package org.bachtx.wibuspringboot.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.ErrorResponse;
import org.bachtx.wibuspringboot.enums.EResponseStatus;
import org.bachtx.wibuspringboot.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class _RestControllerAdvice {
    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return BaseResponse.<ErrorResponse>builder()
                .errors(
                        ex.getBindingResult()
                                .getAllErrors().stream()
                                .map(error -> ErrorResponse.builder()
                                        .detail(
                                                error.getDefaultMessage()
                                        ).target(error.getObjectName())
                                        .build())
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
    public BaseResponse<?> handleEmailAlreadyExistsException(RuntimeException ex) {
        return BaseResponse.builder()
                .errors(
                        List.of(ErrorResponse.builder()
                                .detail(ex.getCause().getMessage())
                                .target("email")
                                .build())
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            UnverifiedUser.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleUnverifiedUser(UnverifiedUser ex) {
        return BaseResponse.builder()
                .errors(
                        List.of(ErrorResponse.builder()
                                .detail(ex.getCause().getMessage())
                                .target("user")
                                .build())
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            UserNotLoggedYetException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleUserNotLoggedYetException(UserNotLoggedYetException ex) {
        return BaseResponse.builder()
                .errors(
                        List.of(ErrorResponse.builder()
                                .detail(ex.getCause().getMessage())
                                .target("user")
                                .build())
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            RegistrationVerifyErrorException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleRegistrationVerifyErrorException(RuntimeException ex) {
        return BaseResponse.builder()
                .errors(
                        List.of(ErrorResponse.builder()
                                .detail(ex.getMessage())
                                .target("registration")
                                .build())
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            ServiceErrorException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleServiceErrorException(RuntimeException ex) {
        return BaseResponse.builder()
                .errors(
                        List.of(ErrorResponse.builder()
                                .detail(ex.getCause().getMessage())
                                .target("service")
                                .build())
                )
                .message(ex.getMessage())
                .status(EResponseStatus.ERROR)
                .build();
    }

    @ExceptionHandler({
            AccessDeniedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseResponse<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return BaseResponse.builder()
                .errors(
                        List.of(ErrorResponse.builder()
                                .detail(request.getRequestURI())
                                .target("access")
                                .build())
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }
}
