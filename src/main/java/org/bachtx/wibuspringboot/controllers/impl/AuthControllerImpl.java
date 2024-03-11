package org.bachtx.wibuspringboot.controllers.impl;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.controllers.AuthController;
import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.RegisterResponse;
import org.bachtx.wibuspringboot.enums.EResponseStatus;
import org.bachtx.wibuspringboot.services.AuthService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public BaseResponse<RegisterResponse> register(RegisterRequest registerRequest) {
        RegisterResponse response = authService.register(registerRequest);
        return BaseResponse.<RegisterResponse>builder()
                .data(response)
                .message("Register successfully")
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
