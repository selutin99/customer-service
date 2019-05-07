package com.galua.onlinestore.offerservice.controllers;

import com.galua.onlinestore.offerservice.entities.PaidType;
import com.galua.onlinestore.offerservice.services.PaidTypeService;
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
public class PaidTypeController {
    @Autowired
    private PaidTypeService paidTypeService;

    @GetMapping("paidtype/{id}")
    public ResponseEntity<PaidType> getPaidTypeByID(@PathVariable("id") int id) {
        try {
            PaidType paidType = paidTypeService.getPaidTypeByID(id);
            log.severe("Тип найден успешно");
            return new ResponseEntity<>(paidType, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.severe("Тип не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("paidtype/customers/{id}")
    public ResponseEntity<List<PaidType>> getPaidTypeByCustomers(@PathVariable("id") int id) {
        try {
            List<PaidType> list = paidTypeService.getPaidTypeByCustomers(id);
            log.severe("Типы найдены успешно");
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            log.severe("Типы не найдены");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("paidtype")
    public ResponseEntity<List<PaidType>> getAllPaidType() {
        List<PaidType> list = paidTypeService.getAllPaidTypes();
        log.severe("Получены все типы");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("paidtype")
    public ResponseEntity addPaidType(@RequestBody PaidType paidType, UriComponentsBuilder builder) {
        try {
            paidTypeService.createPaidType(paidType);
        }
        catch(IllegalArgumentException e){
            log.severe("Попытка добавления существующего типа");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e){
            log.severe("Передан неверный тип");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/paidtype/{id}").buildAndExpand(paidType.getId()).toUri());
        log.severe("Тип добавлен успешно");
        return new ResponseEntity(paidType, headers, HttpStatus.CREATED);
    }

    @PutMapping("paidtype/{id}")
    public ResponseEntity<PaidType> updateCharacteristics(@PathVariable(value = "id") int id,
                                                          @RequestBody PaidType paidType) {
        try {
            PaidType type = paidTypeService.updatePaidType(id, paidType);
            log.severe("Тип обновлен успешно");
            return new ResponseEntity<>(type, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            log.severe("Передан несуществующий тип");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            log.severe("Передан неверный тип");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("paidtype/{id}")
    public ResponseEntity<Void> deletePaidType(@PathVariable("id") int id) {
        try {
            paidTypeService.deletePaidType(id);
            log.severe("Тип удален успешно");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NoSuchElementException e){
            log.severe("Тип не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IllegalArgumentException e){
            log.severe("Тип связан с заказчиком");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}