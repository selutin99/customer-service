package com.galua.onlinestore.offerservice.repositories;

import com.galua.onlinestore.offerservice.entities.PaidType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaidTypeRepo extends CrudRepository<PaidType, Integer> {
    List<PaidType> findByName(String name);
}
