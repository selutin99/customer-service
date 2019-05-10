package com.galua.onlinestore.offerservice.config;

import com.galua.onlinestore.offerservice.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RolesInitializer {
    @Autowired
    private RolesService rolesService;

    @PostConstruct
    public void init(){
        rolesService.checkExist();
    }
}