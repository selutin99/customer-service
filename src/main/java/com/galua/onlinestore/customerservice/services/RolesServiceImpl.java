package com.galua.onlinestore.customerservice.services;

import com.galua.onlinestore.customerservice.entities.Roles;
import com.galua.onlinestore.customerservice.repositories.RolesRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesRepo rolesRepository;

    @Override
    public void checkExist() {
        if(rolesRepository.findByName("ROLE_USER")==null){
            rolesRepository.save(new Roles(1, "ROLE_USER"));
            log.severe("Роль пользователя успешно сохранена");
        }
        else{
            log.severe("Роль пользователя существует");
        }
        if(rolesRepository.findByName("ROLE_ADMIN")==null){
            rolesRepository.save(new Roles(2, "ROLE_ADMIN"));
            log.severe("Роль администратора успешно сохранена");
        }
        else{
            log.severe("Роль администратора существует");
        }
    }
}
