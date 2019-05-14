package com.galua.onlinestore.customerservice.config;

import com.galua.onlinestore.customerservice.services.CustomersService;
import com.galua.onlinestore.customerservice.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EntitiesInitializer {
    @Autowired
    private RolesService rolesService;

    @Autowired
    private CustomersService customersService;

    @PostConstruct
    public void init(){
        rolesService.checkExist();
        customersService.checkExist();
    }
}