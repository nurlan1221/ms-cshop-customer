package az.orient.msshopcustomer.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidCustomerNameValidatorTest {
    private ValidCustomerNameValidator validator=new ValidCustomerNameValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;



    @Test
    void isValidOnlyDigitValueThenReturnFalse() {
        String value="12345";
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(constraintValidatorContext);
        assertFalse(validator.isValid(value, constraintValidatorContext));
    }
    @Test
    void isValidGivenNotOnlyLetterValuesThenReturnFalse() {
        String value="12345@#$!";
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(constraintValidatorContext);
        assertFalse(validator.isValid(value, constraintValidatorContext));
    }
    @Test
    void isValidGivenOnlyLetterValuesThenReturnTrue() {
        String value="Nurlan";
        verify(constraintValidatorContext,never()).buildConstraintViolationWithTemplate(anyString());
        boolean result=validator.isValid(value,constraintValidatorContext);
        assertTrue(result);
    }
    @Test
    void isValidGivenValueLessThan4ThenReturnFalse() {
        String value="Ali";
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(constraintValidatorContext);
        boolean result=validator.isValid(value,constraintValidatorContext);
        assertFalse(result);
    }
    @Test
    void isValidGivenValueGreaterThan20ThenReturnFalse() {
        String value="Nurlandwwrrfdwiigcuwcbgiywgdiygiwwwww";
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(constraintValidatorContext);
        boolean result=validator.isValid(value,constraintValidatorContext);
        assertFalse(result);
    }
    @Test
    void isValidGivenValueIsNullThenReturnFalse() {
        String value=null;
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
        .thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(constraintValidatorContext);
        boolean result=validator.isValid(value,constraintValidatorContext);
        verify(constraintValidatorContext).buildConstraintViolationWithTemplate("Customer name cannot be null");
        verify(constraintViolationBuilder).addConstraintViolation();
    }





}