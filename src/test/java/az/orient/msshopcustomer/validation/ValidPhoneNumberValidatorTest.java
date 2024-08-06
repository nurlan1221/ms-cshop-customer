package az.orient.msshopcustomer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ValidPhoneNumberValidatorTest {
    ValidPhoneNumberValidator validPhoneNumberValidator = new ValidPhoneNumberValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;


    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;


    @Test
    void isInvalidPhoneNumberThenReturnFalse() {
        String value = "123454321234";

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(constraintValidatorContext);

        boolean result = validPhoneNumberValidator.isValid(value, constraintValidatorContext);
        assertFalse(result);
    }


}