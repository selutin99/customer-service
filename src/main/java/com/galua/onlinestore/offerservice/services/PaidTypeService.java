package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.PaidType;

import java.util.List;

public interface PaidTypeService {
    void createPaidType(PaidType paidType);
    PaidType updatePaidType(int id, PaidType paidType);
    void deletePaidType(int id);

    PaidType getPaidTypeByID(int id);

    List<PaidType> getPaidTypeByCustomers(int id);
    List<PaidType> getAllPaidTypes();
}
