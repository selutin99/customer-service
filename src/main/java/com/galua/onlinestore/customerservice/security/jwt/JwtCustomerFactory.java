package com.galua.onlinestore.customerservice.security.jwt;

import com.galua.onlinestore.customerservice.entities.Customers;
import com.galua.onlinestore.customerservice.entities.Roles;
import com.galua.onlinestore.customerservice.entities.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtCustomerFactory {

    public JwtCustomerFactory() {
    }

    public static JwtCustomer create(Customers customer) {
        return new JwtCustomer(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getPhoneNumber(),
                customer.getStatus().equals(Status.ACTIVE),
                mapToGrantedAuthorities(new ArrayList<>(customer.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Roles> customerRoles) {
        return customerRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
