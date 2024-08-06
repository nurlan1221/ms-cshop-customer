package az.orient.msshopcustomer.validation;

import az.orient.msshopcustomer.exception.CustomerNotFoundException;
import az.orient.msshopcustomer.exception.InvalidPhoneNumberException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

public class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {


    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneUtil.parse(value, "AZ");
        } catch (NumberParseException e) {
            throw new InvalidPhoneNumberException("Invalid phone number");
        }
        boolean isValid = phoneUtil.isValidNumber(phoneNumber);
        if (!isValid||value==null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid phone number").addConstraintViolation();
            return false;
        }
        return true;
    }
}
