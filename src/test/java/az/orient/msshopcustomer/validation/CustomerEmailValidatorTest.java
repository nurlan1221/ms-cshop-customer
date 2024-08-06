package az.orient.msshopcustomer.validation;

import az.orient.msshopcustomer.dto.Customerdto;
import az.orient.msshopcustomer.entity.CustomerEntity;
import az.orient.msshopcustomer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Component
@ExtendWith(MockitoExtension.class)
class CustomerEmailValidatorTest {
    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    CustomerEmailValidator customerEmailValidator;


    @Mock
    Customerdto customerdto;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    CustomerRepository customerRepository;

    @Test
    void supportsClass() throws Exception {
        assertTrue(customerEmailValidator.supports(Customerdto.class));
    }

    @Test
    void validate() throws Exception {
        Customerdto customerdto = new Customerdto();
        customerdto.setEmail("john@gmail.com");

        Mockito.when(customerRepository.findByEmail(customerdto.getEmail())).thenReturn(Optional.of(new CustomerEntity()));

        Errors errors = new BeanPropertyBindingResult(customerdto, "customerDto");

        customerEmailValidator.validate(customerdto, errors);

        assertTrue(errors.hasFieldErrors("email"));
        assertEquals("This email already exists", errors.getFieldError("email").getDefaultMessage());
    }


}