package az.orient.msshopcustomer.service;

import az.orient.msshopcustomer.dto.Customerdto;
import az.orient.msshopcustomer.entity.CustomerEntity;
import az.orient.msshopcustomer.exception.CustomerNotFoundException;
import az.orient.msshopcustomer.mapper.CustomerMapper;
import az.orient.msshopcustomer.repository.CustomerRepository;
import az.orient.msshopcustomer.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customerdto createCustomer(Customerdto customerdto) {
        CustomerEntity customerEntity = CustomerMapper.INSTANCE.toCustomerEntity(customerdto);
        customerEntity.setStatus(Status.ACTIVE);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);
        return CustomerMapper.INSTANCE.toCustomerdto(savedCustomerEntity);
    }

    public List<Customerdto> getAllCustomers() {
        List<CustomerEntity> customerEntities = customerRepository.findByStatus(Status.ACTIVE);
        return CustomerMapper.INSTANCE.toCustomerdtoList(customerEntities);
    }

    public Customerdto getCustomerById(Long id) {
        CustomerEntity customerEntity = customerRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id:" + id));
        return CustomerMapper.INSTANCE.toCustomerdto(customerEntity);
    }

    public void deleteCustomerById(Long id) {
        CustomerEntity customerEntity = customerRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id:" + id));
        customerEntity.setStatus(Status.DELETED);
        customerRepository.save(customerEntity);
    }

    public Customerdto updateCustomerById(Long id, Customerdto customerdto) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id:" + id));
        CustomerEntity savedCustomerEntity = CustomerMapper.INSTANCE.updateCustomerEntity(customerdto, customerEntity);
        CustomerEntity saved = customerRepository.save(savedCustomerEntity);
        return CustomerMapper.INSTANCE.toCustomerdto(saved);


    }


}
