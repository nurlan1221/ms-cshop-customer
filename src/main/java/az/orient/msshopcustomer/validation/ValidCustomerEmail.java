package az.orient.msshopcustomer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidCustomerEmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCustomerEmail {
    String message() default "Customer email is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
