package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Customers;

import java.util.List;

public interface CustomersService {
    Customers createCustomer(Customers customer);
    void updateCustomer(int id, Customers customer);
    void deleteCustomer(int id);

    Customers getCustomerByID(int id);
    Customers getCustomerByEmail(String email);
    Customers getCustomerByPhone(String phone);

    List<Customers> getAllCustomers();
}
