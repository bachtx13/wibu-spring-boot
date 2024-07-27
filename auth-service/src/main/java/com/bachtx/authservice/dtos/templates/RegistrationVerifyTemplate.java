package com.bachtx.authservice.dtos.templates;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationVerifyTemplate {
    private String url;
}
