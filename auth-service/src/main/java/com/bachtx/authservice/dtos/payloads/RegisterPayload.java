package com.bachtx.authservice.dtos.payloads;

import com.bachtx.authservice.validators.FieldsMatch;
import com.bachtx.authservice.validators.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
@FieldsMatch(field = "password", fieldMatch = "confirmPassword", message = "Password and confirm password must match!")
public class RegisterPayload {
    @Email(message = "Enter the correct email format!")
    @NotEmpty(message = "Message must not be empty")
    private String email;
    @NotEmpty(message = "Username must not be empty")
    private String username;
    @ValidPassword
    @NotEmpty(message = "Password must not be empty")
    private String password;
    private String confirmPassword;
}
