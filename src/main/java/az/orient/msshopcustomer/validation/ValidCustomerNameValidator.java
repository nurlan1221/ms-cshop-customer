package az.orient.msshopcustomer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCustomerNameValidator implements ConstraintValidator<ValidCustomerName, String> {

    @Override
    public void initialize(ValidCustomerName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            buildCustomValidation(context, "Customer name cannot be null");
            return false;
        }
        if (value.matches("\\d+")) {
            buildCustomValidation(context, "Customer name cannot consist only of numbers");
            return false;
        }
        if (!(value.matches("[a-zA-Z]+"))) {
            buildCustomValidation(context, "Customer name can only contain alphabetic characters");
            return false;
        }
        if (value.length() < 4 || value.length() > 20) {
            buildCustomValidation(context, "Customer name must be between 4 and 20 characters");
            return false;
        }
        return true;
    }

    private void buildCustomValidation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

}
