package com.galua.onlinestore.offerservice.security.jwt;

import com.galua.onlinestore.offerservice.entities.Customers;
import com.galua.onlinestore.offerservice.entities.PaidType;
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
                true,
                mapToGrantedAuthorities(new ArrayList<>(customer.getTypes()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<PaidType> customerPaidTypes) {
        return customerPaidTypes.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
