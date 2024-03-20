package org.bachtx.wibuspringboot.dtos.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bachtx.wibuspringboot.validators.FieldsMatch;
import org.bachtx.wibuspringboot.validators.ValidPassword;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldsMatch(field = "password", fieldMatch = "confirmationPassword", message = "Password muss match")
public class RegisterRequest {
    @Email
    private String email;
    @ValidPassword(message = "Password must be at least 8 characters long and contain a combination of letters, numbers, and special characters")
    private String password;
    private String confirmationPassword;
}
