package org.bachtx.wibuspringboot.controllers.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.controllers.AuthController;
import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.RegisterResponse;
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
    public BaseResponse<RegisterResponse> register(RegisterRequest registerRequest, HttpServletRequest request) {
        RegisterResponse response = authService.register(registerRequest);
        return BaseResponse.<RegisterResponse>builder()
                .data(response)
                .message("Register successfully")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @Override
    public BaseResponse<RegisterResponse> confirmRegistration(UUID token) {
        RegisterResponse response = authService.registrationVerify(token);
        return BaseResponse.<RegisterResponse>builder()
                .data(response)
                .message("Verify successfully")
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
