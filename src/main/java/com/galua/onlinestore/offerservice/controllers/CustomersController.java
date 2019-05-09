package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.offerservice.entities.Customers;
import com.galua.onlinestore.offerservice.services.CustomersService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;

@Log
@RestController
public class CustomersController {
    @Autowired
    private CustomersService customersService;

    @GetMapping("customers/{id}")
    public ResponseEntity<Customers> getCustomersByID(@PathVariable("id") int id) {
        try {
            Customers offer = customersService.getCustomerByID(id);
            log.severe("Заказчик найден успешно");
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.severe("Заказчик не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customers")
    public ResponseEntity<List<Customers>> getAllCustomers() {
        List<Customers> list = customersService.getAllCustomers();
        log.severe("Получены все заказчики");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("customers")
    public ResponseEntity addCustomers(@RequestBody Customers customers, UriComponentsBuilder builder) {
        Customers customer = null;
        try {
            customer = customersService.createCustomer(customers);
        }
        catch(IllegalArgumentException e){
            log.severe("Попытка добавления существующего заказчика");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            log.severe("Передан неверный заказчик");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/customers/{id}").buildAndExpand(customers.getId()).toUri());
        log.severe("Заказчик добавлен успешно");
        return new ResponseEntity(customer, headers, HttpStatus.CREATED);
    }

    @PutMapping("customers/{id}")
    public ResponseEntity<Customers> updateCustomers(@PathVariable(value = "id") int id,
                                                  @RequestBody Customers offer) {
        try {
            customersService.updateCustomer(id, offer);
            log.severe("Сведения о заказчике обновлены успешно");
            return new ResponseEntity<>(offer, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Передан несуществующий заказчик");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передан неверный заказчик");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<Void> deleteCustomers(@PathVariable("id") int id) {
        try {
            customersService.deleteCustomer(id);
            log.severe("Заказчик удалён успешно");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NoSuchElementException e){
            log.severe("Заказчик не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}