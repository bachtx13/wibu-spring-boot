package org.bachtx.wibuspringboot.services.impl;

import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.RegisterResponse;
import org.bachtx.wibuspringboot.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        return null;
    }
}
