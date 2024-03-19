package org.bachtx.wibuspringboot.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.bachtx.wibuspringboot.constants.RegexConstants;

import java.util.regex.Pattern;

public class ValidPasswordConstraint implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches(RegexConstants.VALID_PASSWORD_REGEX, value);
    }
}
