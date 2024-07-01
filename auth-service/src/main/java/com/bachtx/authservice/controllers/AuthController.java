package com.bachtx.authservice.controllers;

import com.bachtx.authservice.dtos.payloads.LoginPayload;
import com.bachtx.authservice.dtos.payloads.RegisterPayload;
import com.bachtx.authservice.dtos.requests.LoginResponse;
import com.bachtx.authservice.dtos.requests.RegisterResponse;
import com.bachtx.authservice.services.IUserService;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseTemplate<LoginResponse> login(@RequestBody LoginPayload loginPayload) {
        LoginResponse loginResponse = userService.login(loginPayload);
        return ResponseTemplate.<LoginResponse>builder()
                .data(loginResponse)
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseTemplate<RegisterResponse> register(@RequestBody @Valid RegisterPayload registerPayload) {
        RegisterResponse registerResponse = userService.register(registerPayload);
        return ResponseTemplate.<RegisterResponse>builder()
                .data(registerResponse)
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "registration-verify/{uuid-token}", method = RequestMethod.GET)
    public ResponseTemplate<RegisterResponse> registrationVerify(@PathVariable("uuid-token") UUID uuidToken) {
        RegisterResponse registerResponse = userService.registrationVerify(uuidToken);
        return ResponseTemplate.<RegisterResponse>builder()
                .data(registerResponse)
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
