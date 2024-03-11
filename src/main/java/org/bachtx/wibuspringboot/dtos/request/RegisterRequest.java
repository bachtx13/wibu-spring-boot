package org.bachtx.wibuspringboot.dtos.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bachtx.wibuspringboot.validators.ValidPassword;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Builder
public class RegisterRequest {
    @Email
    private String email;
    @ValidPassword
    private String password;
    private String confirmationPassword;
}
