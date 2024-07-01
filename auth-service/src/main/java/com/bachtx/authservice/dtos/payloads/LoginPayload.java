package com.bachtx.authservice.dtos.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Builder
@AllArgsConstructor
@Getter
@Setter
public class LoginPayload {
    private String email;
    private String password;
}
