package com.galua.onlinestore.customerservice.repositories;

import com.galua.onlinestore.customerservice.entities.Roles;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepo extends CrudRepository<Roles, Integer> {
    Roles findByName(String name);
}
