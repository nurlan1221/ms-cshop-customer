package az.orient.msshopcustomer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCustomerEmailValidator implements ConstraintValidator<ValidCustomerEmail, String> {
    @Override
    public void initialize(ValidCustomerEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.contains("@")) {
            return false;
        }
        if (value == null) {
            return false;
        }
        return true;
    }
}
