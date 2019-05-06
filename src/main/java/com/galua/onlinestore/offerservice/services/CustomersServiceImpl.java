package com.galua.onlinestore.offerservice.services;

import com.galua.onlinestore.offerservice.entities.Customers;
import com.galua.onlinestore.offerservice.repositories.CustomersRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class CustomersServiceImpl implements CustomerService {

    @Autowired
    private CustomersRepo customerRepositoty;

    @Override
    public Customers createCustomer(Customers customer) {
        if(customer==null){
            log.severe("Не было передано заказчика");
            throw new IllegalArgumentException("Заказчик не передан");
        }
        List<Customers> list = customerRepositoty.findByEmail(customer.getEmail());
        if (list.size() > 0) {
            log.severe("Был передан существующий заказчик");
            throw new IllegalArgumentException("Заказчик уже существует");
        }
        else {
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
        findCustomer.setPassword(customer.getPassword());
        findCustomer.setAddress(customer.getAddress());
        findCustomer.setTypes(customer.getTypes());

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
    public List<Customers> getAllCustomers() {
        log.severe("Получение всех заказчиков");
        List<Customers> listCustomers = new ArrayList<>();
        customerRepositoty.findAll().forEach(e -> listCustomers.add(e));
        return listCustomers;
    }
}

