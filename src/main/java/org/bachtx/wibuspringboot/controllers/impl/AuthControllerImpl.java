package org.bachtx.wibuspringboot.controllers.impl;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.controllers.AuthController;
import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.enums.EResponseStatus;
import org.bachtx.wibuspringboot.services.AuthService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
@CrossOrigin
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public BaseResponse<String> register(RegisterRequest registerRequest) {
//        RegisterResponse response = authService.register(registerRequest);
        return BaseResponse.<String>builder()
                .data("")
                .message("Register successfully")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @Override
    public BaseResponse<String> confirmRegistration(UUID token) {
        return BaseResponse.<String>builder()
                .data(token.toString())
                .message("Register successfully")
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
