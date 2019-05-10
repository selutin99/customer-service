package com.galua.onlinestore.customerservice.repositories;

import com.galua.onlinestore.customerservice.entities.Addresses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressesRepo extends CrudRepository<Addresses, Integer> {
    List<Addresses> findByCity(String city);
    List<Addresses> findByState(String state);
    List<Addresses> findByCountry(String country);

    List<Addresses> findByCityAndStateAndCountry(String city, String state, String country);
}