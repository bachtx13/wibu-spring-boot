package com.bachtx.authservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsMatchConstraint implements ConstraintValidator<FieldsMatch, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(obj)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(obj)
                .getPropertyValue(fieldMatch);

        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}
