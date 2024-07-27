package com.bachtx.authservice.validators;

import com.bachtx.authservice.constants.RegexConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidPasswordConstraint implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches(RegexConstants.VALID_PASSWORD_REGEX, value);
    }
}
