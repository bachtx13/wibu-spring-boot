package com.bachtx.authservice.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class LoginResponse {
    private String token;
    private Date expires;
}
