package az.orient.msshopcustomer.controller;

import az.orient.msshopcustomer.dto.Customerdto;
import az.orient.msshopcustomer.exception.CustomerNotFoundException;
import az.orient.msshopcustomer.service.CustomerService;
import az.orient.msshopcustomer.validation.CustomerEmailValidator;
import az.orient.msshopcustomer.validation.ValidPhoneNumberValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @MockBean
    CustomerEmailValidator customerEmailValidator;

    @MockBean
    ValidPhoneNumberValidator validPhoneNumberValidator;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createCustomerGivenValidDtoThenReturn201() throws Exception {
//setup
        Customerdto customerdto = new Customerdto();
        customerdto.setFirstName("Nurlan");
        customerdto.setLastName("Bayramov");
        customerdto.setEmail("john@doe.com");
        customerdto.setAddress("America");
        customerdto.setPhoneNumber("+994555555555");

        //when
        Mockito.doNothing().when(customerEmailValidator).validate(Mockito.any(), Mockito.any());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .content(objectMapper.writeValueAsString(customerdto))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        //expect
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    void createCustomerGivenInvalidDtoThenReturn400() throws Exception {
        Customerdto customerdto = new Customerdto();
        customerdto.setFirstName("Nurlan");
        customerdto.setLastName("Bayramov");
        customerdto.setEmail("john@doe.com");
        customerdto.setPhoneNumber("+994555555555");
        Mockito.doNothing().when(customerEmailValidator).validate(Mockito.any(), Mockito.any());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .content(objectMapper.writeValueAsString(customerdto))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        //expect
        assertEquals(400, mvcResult.getResponse().getStatus());

    }

    @Test
    void getCustomerGivenValidIdThenReturn200() throws Exception {
        Long customerId = 1L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + customerId)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        Mockito.verify(customerService).getCustomerById(customerId);

    }

    @Test
    void getCustomerGivenInvalidIdThenReturn404() throws Exception {
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenThrow(new CustomerNotFoundException("Customer not found :" + customerId));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + customerId)).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
        verify(customerService).getCustomerById(customerId);
    }

    @Test
    void deleteCustomerGivenValidIdThenReturn200() throws Exception {
        Long customerId = 1L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + customerId)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        verify(customerService).deleteCustomerById(customerId);
    }

    @Test
    void deleteCustomerGivenInvalidIdThenReturn404() throws Exception {
        Long customerId = 1L;
        doThrow(new CustomerNotFoundException("Customer not found with id:" + customerId)).when(customerService).deleteCustomerById(customerId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + customerId)).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
        verify(customerService).deleteCustomerById(customerId);
    }

    @Test
    void updateCustomerGivenValidIdThenReturn200() throws Exception {
        Long customerId = 1L;
        Customerdto customerdto = new Customerdto();
        customerdto.setFirstName("Nurlan");
        customerdto.setLastName("Bayramov");
        customerdto.setEmail("john@doe.com");
        customerdto.setPhoneNumber("+994555555555");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/customers/" + customerId)
                .content(objectMapper.writeValueAsString(customerdto))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        verify(customerService).updateCustomerById(customerId, customerdto);
    }

    @Test
    void updateCustomerGivenInvalidIdAndDtoThenReturn404() throws Exception {
        Long customerId = 1L;
        Customerdto customerdto = new Customerdto();
        customerdto.setFirstName("Nurlan");
        customerdto.setLastName("Bayramov");
        customerdto.setEmail("john@doe.com");
        customerdto.setPhoneNumber("+994555555555");
        when(customerService.updateCustomerById(customerId, customerdto)).thenThrow(new CustomerNotFoundException("Customer not found with id: " + customerId));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/customers/" + customerId)
                .content(objectMapper.writeValueAsString(customerdto)).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
        verify(customerService).updateCustomerById(customerId, customerdto);
    }

    @Test
    void handleMethodNotArgumentNotValidException() throws Exception {

        Customerdto invalidCustomerDto = new Customerdto();
        invalidCustomerDto.setFirstName("Nurlan");
        invalidCustomerDto.setLastName("Bayramov");
        invalidCustomerDto.setEmail("@");
        invalidCustomerDto.setPhoneNumber("+994556433212");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .content(objectMapper.writeValueAsString(invalidCustomerDto)).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
        String responseContent = mvcResult.getResponse().getContentAsString();

        assertEquals("application/json", mvcResult.getResponse().getContentType());

        assertTrue(responseContent.contains("Adress is required"));

    }


}