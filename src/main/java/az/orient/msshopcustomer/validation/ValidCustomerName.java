package az.orient.msshopcustomer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidCustomerNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCustomerName {
    String message() default "Customer name is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

