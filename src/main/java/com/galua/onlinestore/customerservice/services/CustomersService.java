package com.galua.onlinestore.customerservice.services;

import com.galua.onlinestore.customerservice.entities.Customers;

import java.util.List;

public interface CustomersService {
    Customers createCustomer(Customers customer);
    void updateCustomer(int id, Customers customer);
    void deleteCustomer(int id);

    Customers getCustomerByID(int id);
    Customers getCustomerByEmail(String email);

    List<Customers> getAllCustomers();

    void checkExist();
}
