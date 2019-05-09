package com.galua.onlinestore.offerservice.repositories;

import com.galua.onlinestore.offerservice.entities.Customers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepo extends CrudRepository<Customers, Integer> {
    List<Customers> findByFirstName(String firstName);
    List<Customers> findByLastName(String lastName);
    List<Customers> findByFirstNameAndLastName(String firstName, String lastName);

    Customers findByEmail(String email);
    Customers findByPhoneNumber(String phoneNumber);

    List<Customers> findByEmailOrPhoneNumber(String email, String phoneNumber);

}

