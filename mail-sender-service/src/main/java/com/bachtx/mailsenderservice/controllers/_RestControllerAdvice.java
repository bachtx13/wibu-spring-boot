package com.bachtx.mailsenderservice.controllers;

import com.bachtx.wibucommon.dtos.response.ErrorResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class _RestControllerAdvice {
    @ExceptionHandler(MailSendException.class)
    @ResponseStatus
    public ResponseTemplate<?> handleMailSendException(MailSendException ex) {
        return ResponseTemplate.builder()
                .errors(
                        List.of(_extractErrorFromException(ex, null))
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
