package org.bachtx.wibuspringboot.controllers;

import jakarta.validation.Valid;
import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public interface AuthController {
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    BaseResponse<String> register(@Valid RegisterRequest registerRequest);

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "confirm-registration/{token}", method = RequestMethod.GET)
    BaseResponse<String> confirmRegistration(@PathVariable UUID token);
}
