package com.galua.onlinestore.customerservice.controllers;

import com.galua.onlinestore.customerservice.entities.Addresses;
import com.galua.onlinestore.customerservice.services.AddressesService;
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
public class AddressController {
    @Autowired
    private AddressesService addressesService;

    @GetMapping("addresses/{id}")
    public ResponseEntity<Addresses> getAddressesByID(@PathVariable("id") int id) {
        try {
            Addresses address = addressesService.getAddressByID(id);
            log.severe("Адрес найден успешно");
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.severe("Адрес не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("addresses")
    public ResponseEntity<List<Addresses>> getAllAddresses() {
        List<Addresses> list = addressesService.getAllAddresses();
        log.severe("Получены все адресы");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("addresses")
    public ResponseEntity addAddress(@RequestBody Addresses address, UriComponentsBuilder builder) {
        try {
            addressesService.createAddress(address);
        }
        catch(IllegalArgumentException e){
            log.severe("Попытка добавления существующего адреса");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            log.severe("Передан неверный адрес");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addresses/{id}").buildAndExpand(address.getId()).toUri());
        log.severe("Адрес добавлен успешно");
        return new ResponseEntity(address, headers, HttpStatus.CREATED);
    }

    @PutMapping("addresses/{id}")
    public ResponseEntity<Addresses> updateAddresses(@PathVariable(value = "id") int id,
                                                      @RequestBody Addresses addresses) {
        try {
            addressesService.updateAddress(id, addresses);
            log.severe("Адрес обновлен успешно");
            addresses.setId(id);
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Передан несуществующий адрес");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передана неверный адрес");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("addresses/{id}")
    public ResponseEntity<Void> deleteAddresses(@PathVariable("id") int id) {
        try {
            addressesService.deleteAddress(id);
            log.severe("Адрес удален успешно");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NoSuchElementException e){
            log.severe("Адрес не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}