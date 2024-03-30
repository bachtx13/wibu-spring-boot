package org.bachtx.wibuspringboot.controllers.impl;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.controllers.UserController;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.UserInfoResponse;
import org.bachtx.wibuspringboot.enums.EResponseStatus;
import org.bachtx.wibuspringboot.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@AllArgsConstructor
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public BaseResponse<UserInfoResponse> getUserInfo() {
        UserInfoResponse userInfoResponse = userService.getUserInfo();
        return BaseResponse.<UserInfoResponse>builder()
                .data(userInfoResponse)
                .message("Get user info successfully")
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
