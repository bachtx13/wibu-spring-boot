package com.bachtx.authservice.services;

import com.bachtx.authservice.dtos.payloads.LoginPayload;
import com.bachtx.authservice.dtos.payloads.RegisterPayload;
import com.bachtx.authservice.dtos.requests.LoginResponse;
import com.bachtx.authservice.dtos.requests.RegisterResponse;

import java.util.UUID;

public interface IUserService {
    LoginResponse login(LoginPayload loginPayload);

    RegisterResponse register(RegisterPayload registerPayload);

    RegisterResponse registrationVerify(UUID token);
}
