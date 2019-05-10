package com.galua.onlinestore.customerservice.services;

import com.galua.onlinestore.customerservice.entities.PaidType;
import com.galua.onlinestore.customerservice.repositories.PaidTypeRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class PaidTypeServiceImpl implements PaidTypeService {

    @Autowired
    private PaidTypeRepo paidTypeRepository;

    @Autowired
    private CustomersService customersService;

    @Override
    public void createPaidType(PaidType paidType) {
        if(paidType==null){
            log.severe("Был передан пустой тип");
            throw new IllegalArgumentException("Тип не передан");
        }
        List<PaidType> list = paidTypeRepository.findByName(paidType.getName());
        if (list.size() > 0) {
            log.severe("Был передан существующий тип");
            throw new IllegalArgumentException("Тип уже существует");
        }
        else {
            paidTypeRepository.save(paidType);
            log.severe("Сохранение типа: " +paidType);
        }
    }

    @Override
    public PaidType updatePaidType(int id, PaidType paidType) {
        PaidType findPaidType = getPaidTypeByID(id);
        findPaidType.setName(paidType.getName());

        List<PaidType> list = paidTypeRepository.findByName(paidType.getName());
        if(paidType.getName().equals(findPaidType.getName())) {
            list.remove(findPaidType);
        }
        if(list.size()>0){
            throw new IllegalArgumentException("Тип уже существует");
        }
        paidTypeRepository.save(findPaidType);

        log.severe("Обновление типа: "+findPaidType);
        return findPaidType;
    }

    @Override
    public void deletePaidType(int id) {
        log.severe("Удаление типа с id="+id);
        if(getPaidTypeByID(id).getCustomers().isEmpty()) {
            paidTypeRepository.delete(getPaidTypeByID(id));
        }
        else{
            throw new IllegalArgumentException("Тип связан с заказчиком");
        }
    }

    @Override
    public PaidType getPaidTypeByID(int id) {
        log.severe("Получение типа с id="+id);
        return paidTypeRepository.findById(id).get();
    }

    @Override
    public List<PaidType> getPaidTypeByCustomers(int id) {
        log.severe("Получение типов по заказчику с id="+id);
        List<PaidType> listOfPaidTypes = new ArrayList<>();
        paidTypeRepository.findByCustomers_Id(id).forEach(e -> listOfPaidTypes.add(e));
        return listOfPaidTypes;
    }

    @Override
    public List<PaidType> getAllPaidTypes() {
        log.severe("Получение всех типов");
        List<PaidType> listOfPaidTypes = new ArrayList<>();
        paidTypeRepository.findAll().forEach(e -> listOfPaidTypes.add(e));
        return listOfPaidTypes;
    }
}