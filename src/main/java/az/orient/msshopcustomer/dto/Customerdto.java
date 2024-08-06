package az.orient.msshopcustomer.dto;

import az.orient.msshopcustomer.validation.ValidCustomerEmail;
import az.orient.msshopcustomer.validation.ValidCustomerName;
import az.orient.msshopcustomer.validation.ValidPhoneNumber;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class Customerdto {
    @ValidCustomerName
    private String firstName;
    @ValidCustomerName
    private String lastName;
    @NotNull(message = "Phone number is required")
    @ValidPhoneNumber
    private String phoneNumber;
    @ValidCustomerEmail
    private String email;
    @NotNull(message = "Adress is required")
    private String address;
    private Date birthDate;

}
