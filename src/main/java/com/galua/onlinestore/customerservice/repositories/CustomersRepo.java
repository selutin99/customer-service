package com.galua.onlinestore.customerservice.repositories;

import com.galua.onlinestore.customerservice.entities.Customers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepo extends CrudRepository<Customers, Integer> {
    List<Customers> findByFirstName(String firstName);
    List<Customers> findByLastName(String lastName);
    List<Customers> findByFirstNameAndLastName(String firstName, String lastName);

    Customers findByEmail(String email);

    List<Customers> findByEmailOrPhoneNumber(String email, String phoneNumber);

}

