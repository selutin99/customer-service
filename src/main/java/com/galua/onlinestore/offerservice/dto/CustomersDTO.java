package com.galua.onlinestore.offerservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.galua.onlinestore.offerservice.entities.Customers;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomersDTO {
    private int id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;

    public Customers toCustomer(){
        Customers customer = new Customers();
        customer.setId(id);
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phone);

        return customer;
    }

    public static CustomersDTO fromCustomer(Customers customer) {
        CustomersDTO userDto = new CustomersDTO();
        userDto.setId(customer.getId());
        userDto.setEmail(customer.getEmail());
        userDto.setFirstName(customer.getFirstName());
        userDto.setLastName(customer.getLastName());
        userDto.setPhone(customer.getPhoneNumber());

        return userDto;
    }
}
