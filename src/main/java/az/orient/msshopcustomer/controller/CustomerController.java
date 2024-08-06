package az.orient.msshopcustomer.controller;


import az.orient.msshopcustomer.dto.Customerdto;
import az.orient.msshopcustomer.service.CustomerService;
import az.orient.msshopcustomer.validation.CustomerEmailValidator;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerEmailValidator customerEmailValidator;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        if (binder.getTarget() != null && customerEmailValidator.supports(binder.getTarget().getClass())) {
            binder.addValidators(customerEmailValidator);
        }
    }

    @GetMapping
    public List<Customerdto> getCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customerdto createCustomer(@Valid @RequestBody Customerdto customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping(path = "{id}")
    public Customerdto getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);

    }

    @DeleteMapping(path = "{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

    @PutMapping(path = "{id}")
    public Customerdto updateCustomerById(@PathVariable Long id, @RequestBody Customerdto customer) {
        return customerService.updateCustomerById(id, customer);
    }

}
