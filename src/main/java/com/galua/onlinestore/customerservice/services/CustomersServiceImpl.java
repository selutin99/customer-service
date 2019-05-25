package com.galua.onlinestore.customerservice.services;

import com.galua.onlinestore.customerservice.entities.Customers;
import com.galua.onlinestore.customerservice.entities.Status;
import com.galua.onlinestore.customerservice.repositories.CustomersRepo;
import com.galua.onlinestore.customerservice.repositories.RolesRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log
@Service
public class CustomersServiceImpl implements CustomersService {

    @Autowired
    private CustomersRepo customerRepositoty;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Customers createCustomer(Customers customer) {
        if(customer==null){
            log.severe("Не было передано заказчика");
            throw new IllegalArgumentException("Заказчик не передан");
        }
        List<Customers> list = customerRepositoty.findByEmailOrPhoneNumber(customer.getEmail(), customer.getPhoneNumber());
        if (list.size() > 0) {
            log.severe("Был передан существующий заказчик");
            throw new IllegalArgumentException("Заказчик уже существует");
        }
        else {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customer.setRoles(Arrays.asList(rolesRepo.findByName("ROLE_USER")));
            customer.setStatus(Status.ACTIVE);

            customerRepositoty.save(customer);
            log.severe("Сохранение заказчика: " +customer);
            return customer;
        }
    }

    @Override
    public void updateCustomer(int id, Customers customer) {
        Customers findCustomer = getCustomerByID(id);

        findCustomer.setFirstName(customer.getFirstName());
        findCustomer.setLastName(customer.getLastName());
        findCustomer.setEmail(customer.getEmail());
        findCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));
        findCustomer.setAddress(customer.getAddress());
        findCustomer.setTypes(customer.getTypes());
        findCustomer.setStatus(Status.ACTIVE);
        findCustomer.setRoles(customer.getRoles());

        List<Customers> list = customerRepositoty.findByEmailOrPhoneNumber(customer.getEmail(), customer.getPhoneNumber());
        if(customer.getEmail().equals(findCustomer.getEmail()) || customer.getPhoneNumber().equals(findCustomer.getPhoneNumber())) {
            list.remove(findCustomer);
        }
        if(list.size()>0){
            throw new IllegalArgumentException("Заказчик уже существует");
        }
        customerRepositoty.save(findCustomer);

        log.severe("Обновление заказчика: "+findCustomer);
    }

    @Override
    public void deleteCustomer(int id) {
        log.severe("Удаление заказчика с id="+id);
        customerRepositoty.delete(getCustomerByID(id));
    }

    @Override
    public Customers getCustomerByID(int id) {
        log.severe("Получение заказчика с id="+id);
        return customerRepositoty.findById(id).get();
    }

    @Override
    public Customers getCustomerByEmail(String email) {
        log.severe("Получение заказчика с email: "+email);
        return customerRepositoty.findByEmail(email);
    }

    @Override
    public List<Customers> getAllCustomers() {
        log.severe("Получение всех заказчиков");
        List<Customers> listCustomers = new ArrayList<>();
        customerRepositoty.findAll().forEach(e -> listCustomers.add(e));
        return listCustomers;
    }
}

