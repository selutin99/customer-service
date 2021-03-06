package com.galua.onlinestore.customerservice.repositories;

import com.galua.onlinestore.customerservice.entities.PaidType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaidTypeRepo extends CrudRepository<PaidType, Integer> {
    List<PaidType> findByName(String name);

    List<PaidType> findByCustomers_Id(int id);
}
