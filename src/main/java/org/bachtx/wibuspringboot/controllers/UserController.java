package org.bachtx.wibuspringboot.controllers;

import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.UserInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserController {
    @RequestMapping(value = "get-info", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    BaseResponse<UserInfoResponse> getUserInfo();
}
