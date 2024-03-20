package org.bachtx.wibuspringboot.controllers;

import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.ErrorResponse;
import org.bachtx.wibuspringboot.enums.EResponseStatus;
import org.bachtx.wibuspringboot.exceptions.EmailAlreadyExistsException;
import org.bachtx.wibuspringboot.exceptions.RegistrationVerifyErrorException;
import org.bachtx.wibuspringboot.exceptions.ServiceErrorException;
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
    public BaseResponse<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return BaseResponse.<ErrorResponse>builder()
                .error(
                        ErrorResponse.builder()
                                .detail(
                                        ex.getBindingResult()
                                                .getAllErrors()
                                                .get(0)
                                                .getDefaultMessage()
                                ).build()
                )
                .message("Validation fail")
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<ErrorResponse> handleEmailAlreadyExistsException(RuntimeException ex) {
        return BaseResponse.<ErrorResponse>builder()
                .error(
                        ErrorResponse.builder()
                                .detail(ex.getCause().getMessage())
                                .build()
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            RegistrationVerifyErrorException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<ErrorResponse> handleRegistrationVerifyErrorException(RuntimeException ex) {
        return BaseResponse.<ErrorResponse>builder()
                .error(
                        ErrorResponse.builder()
                                .detail(ex.getMessage())
                                .build()
                )
                .message(ex.getMessage())
                .status(EResponseStatus.FAIL)
                .build();
    }

    @ExceptionHandler({
            ServiceErrorException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<ErrorResponse> handleServiceErrorException(RuntimeException ex) {
        return BaseResponse.<ErrorResponse>builder()
                .error(
                        ErrorResponse.builder()
                                .detail(ex.getCause().getMessage())
                                .build()
                )
                .message(ex.getMessage())
                .status(EResponseStatus.ERROR)
                .build();
    }
}
