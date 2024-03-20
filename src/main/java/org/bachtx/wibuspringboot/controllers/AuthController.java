package org.bachtx.wibuspringboot.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.bachtx.wibuspringboot.dtos.request.LoginRequest;
import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.LoginResponse;
import org.bachtx.wibuspringboot.dtos.response.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface AuthController {
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    BaseResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request);

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "registration-verify/{token}", method = RequestMethod.GET)
    BaseResponse<RegisterResponse> confirmRegistration(@PathVariable UUID token);

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "login", method = RequestMethod.POST)
    BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse);

}
