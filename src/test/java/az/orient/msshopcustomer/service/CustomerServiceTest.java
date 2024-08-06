package az.orient.msshopcustomer.service;

import az.orient.msshopcustomer.dto.Customerdto;
import az.orient.msshopcustomer.entity.CustomerEntity;
import az.orient.msshopcustomer.exception.CustomerNotFoundException;
import az.orient.msshopcustomer.repository.CustomerRepository;
import az.orient.msshopcustomer.type.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;


    @InjectMocks
    CustomerService customerService;


    @Test
    void updateCustomerGivenValidIdThenReturnUpdatedCustomerDto() {

        Long customerId = 1L;
        Customerdto request = new Customerdto();
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName("Nurlan");
        customerEntity.setLastName("Bayramov");
        customerEntity.setEmail("john@doe.com");
        customerEntity.setAddress("America");
        customerEntity.setPhoneNumber("+994555555555");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);

        Customerdto response = customerService.updateCustomerById(customerId, request);

        assertNotNull(response);
        Mockito.verify(customerRepository, Mockito.times(1)).save(customerEntity);
    }

    @Test
    void updateCustomerGivenInvalidIdThenThrowCustomerNotFoundException() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        CustomerNotFoundException result = assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomerById(customerId, new Customerdto()));

        assertNotNull(result);
    }
    @Test
    void deleteCustomerGivenValidIdThenReturnTrue() {
        Long customerId = 1L;
        CustomerEntity customerEntity=new CustomerEntity();
        customerEntity.setFirstName("Nurlan");
        customerEntity.setStatus(Status.ACTIVE);
        when(customerRepository.findByIdAndStatus(customerId,Status.ACTIVE)).thenReturn(Optional.of(customerEntity));
        customerService.deleteCustomerById(customerId);
        verify(customerRepository).findByIdAndStatus(customerId,Status.ACTIVE);
        verify(customerRepository).save(customerEntity);
        assertEquals(Status.DELETED,customerEntity.getStatus());
    }

    @Test
    void deleteCustomerGivenInvalidIdThenThrowCustomerNotFoundException() {

        Long customerId = 1L;
        when(customerRepository.findByIdAndStatus(customerId,Status.ACTIVE)).thenReturn(Optional.empty());
        CustomerNotFoundException result = assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomerById(customerId));
        assertNotNull(result);
    }

    @Test
    void getCustomerGivenValidIdThenReturnCustomerDto() {
        Long customerId = 1L;
        CustomerEntity customerEntity=new CustomerEntity();
        customerEntity.setId(customerId);
        customerEntity.setStatus(Status.ACTIVE);
        when(customerRepository.findByIdAndStatus(customerId,Status.ACTIVE)).thenReturn(Optional.of(customerEntity));
        Customerdto response = customerService.getCustomerById(customerId);
        assertNotNull(response);
    }

    @Test
    void getCustomerGivenInvalidIdThenThrowCustomerNotFoundException() {
        Long customerId = 1L;
        when(customerRepository.findByIdAndStatus(customerId,Status.ACTIVE)).thenReturn(Optional.empty());
        CustomerNotFoundException result = assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(customerId));
        assertNotNull(result);
    }

}