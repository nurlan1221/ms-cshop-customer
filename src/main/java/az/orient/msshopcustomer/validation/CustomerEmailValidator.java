package az.orient.msshopcustomer.validation;

import az.orient.msshopcustomer.dto.Customerdto;
import az.orient.msshopcustomer.entity.CustomerEntity;
import az.orient.msshopcustomer.repository.CustomerRepository;
import az.orient.msshopcustomer.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerEmailValidator implements Validator {
    private final CustomerRepository customerRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Customerdto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customerdto customerdto = (Customerdto) target;
        String email = customerdto.getEmail();
        Optional<CustomerEntity> customerEntity = customerRepository.findByEmail(email);
        if (customerEntity.isPresent()) {
            errors.rejectValue("email", "", "This email already exists");
        }


    }
}
