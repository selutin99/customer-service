package com.galua.onlinestore.customerservice.services;

import com.galua.onlinestore.customerservice.entities.Addresses;

import java.util.List;

public interface AddressesService {
    void createAddress(Addresses address);
    void updateAddress(int id, Addresses address);
    void deleteAddress(int id);

    Addresses getAddressByID(int id);
    List<Addresses> getAllAddresses();
}
