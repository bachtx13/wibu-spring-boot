package com.bachtx.gateway.dtos.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthenticationTokenData {
    private String username;
}
