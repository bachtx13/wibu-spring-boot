package org.bachtx.wibuspringboot.controllers;

import jakarta.validation.Valid;
import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface AuthController {
    @ResponseStatus(HttpStatus.CREATED)
    BaseResponse<RegisterResponse> register(@Valid RegisterRequest registerRequest);
}
