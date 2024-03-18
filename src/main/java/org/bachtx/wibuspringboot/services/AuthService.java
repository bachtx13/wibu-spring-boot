package org.bachtx.wibuspringboot.services;

import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);
}
