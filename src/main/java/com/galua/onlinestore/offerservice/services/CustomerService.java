package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Customers;

import java.util.List;

public interface CustomerService {
    Customers createCustomer(Customers customer);
    void updateCustomer(int id, Customers customer);
    void deleteCustomer(int id);

    Customers getCustomerByID(int id);
    List<Customers> getAllCustomers();
}
