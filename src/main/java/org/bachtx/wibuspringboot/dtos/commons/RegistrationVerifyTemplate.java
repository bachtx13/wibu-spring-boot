package org.bachtx.wibuspringboot.dtos.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationVerifyTemplate {
    private String url;
}
