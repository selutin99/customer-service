package com.galua.onlinestore.customerservice.security;

import com.galua.onlinestore.customerservice.entities.Customers;
import com.galua.onlinestore.customerservice.security.jwt.JwtCustomer;
import com.galua.onlinestore.customerservice.security.jwt.JwtCustomerFactory;
import com.galua.onlinestore.customerservice.services.CustomersService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log
public class CustomersUserDetailService implements UserDetailsService {

    @Autowired
    private CustomersService customersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customers customer = customersService.getCustomerByEmail(email);

        if (customer == null) {
            throw new UsernameNotFoundException("Заказчик не найден!");
        }

        log.severe("Заказчик загружен");
        JwtCustomer jwtCustomer = JwtCustomerFactory.create(customer);
        return jwtCustomer;
    }
}
