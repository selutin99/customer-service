package com.galua.onlinestore.offerservice.repositories;

import com.galua.onlinestore.offerservice.entities.Roles;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepo extends CrudRepository<Roles, Integer> {
    Roles findByName(String name);
}
