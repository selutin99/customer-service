package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Addresses;

import java.util.List;

public interface AddressesService {
    void createAddress(Addresses address);
    void updateAddress(int id, Addresses address);
    void deleteAddress(int id);

    Addresses getAddressByID(int id);
    List<Addresses> getAllAddresses();
}
