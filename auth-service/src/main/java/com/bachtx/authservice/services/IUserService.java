package com.bachtx.authservice.services;

import com.bachtx.authservice.dtos.payloads.LoginPayload;
import com.bachtx.authservice.dtos.payloads.RegisterPayload;
import com.bachtx.authservice.dtos.responses.LoginResponse;
import com.bachtx.authservice.dtos.responses.RegisterResponse;
import com.bachtx.authservice.dtos.responses.UserInfoResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    LoginResponse login(LoginPayload loginPayload);

    RegisterResponse register(RegisterPayload registerPayload);

    RegisterResponse registrationVerify(UUID token);

    void initAdminUser();

    UserInfoResponse getUserInfo(String token);

    List<UserInfoResponse> getUserList(String token);
}
