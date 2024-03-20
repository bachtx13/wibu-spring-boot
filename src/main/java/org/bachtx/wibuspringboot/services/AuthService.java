package org.bachtx.wibuspringboot.services;

import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.RegisterResponse;

import java.util.UUID;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);

    RegisterResponse registrationVerify(UUID token);
}