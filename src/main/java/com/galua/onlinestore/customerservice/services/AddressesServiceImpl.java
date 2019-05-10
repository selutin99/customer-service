package com.galua.onlinestore.customerservice.services;

import com.galua.onlinestore.customerservice.entities.Addresses;
import com.galua.onlinestore.customerservice.repositories.AddressesRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class AddressesServiceImpl implements AddressesService {

    @Autowired
    private AddressesRepo addressesRepository;

    @Override
    public void createAddress(Addresses address) {
        if(address==null){
            log.severe("Был передан пустой адрес");
            throw new IllegalArgumentException("Адрес не передан");
        }
        List<Addresses> list = addressesRepository.findByCityAndStateAndCountry(address.getCity(), address.getState(), address.getCountry());
        if (list.size() > 0) {
            log.severe("Был передан существующий адрес");
            throw new IllegalArgumentException("Адрес уже существует");
        }
        else {
            addressesRepository.save(address);
            log.severe("Сохранение адреса: " +address);
        }
    }

    @Override
    public void updateAddress(int id, Addresses address) {
        Addresses findAddress = getAddressByID(id);

        findAddress.setCity(address.getCity());
        findAddress.setCountry(address.getCountry());
        findAddress.setState(address.getState());
        findAddress.setCustomers(address.getCustomers());

        List<Addresses> list = addressesRepository.findByCityAndStateAndCountry(address.getCity(), address.getState(), address.getCountry());
        if(address.getCity().equals(findAddress.getCity()) &&
           address.getState().equals(findAddress.getState()) &&
           address.getCountry().equals(findAddress.getCountry())) {
            list.remove(findAddress);
        }
        if(list.size()>0){
            throw new IllegalArgumentException("Адрес уже существует");
        }
        addressesRepository.save(findAddress);

        log.severe("Обновление адреса: "+findAddress);
    }

    @Override
    public void deleteAddress(int id) {
        log.severe("Удаление адреса с id="+id);
        addressesRepository.delete(getAddressByID(id));
    }

    @Override
    public Addresses getAddressByID(int id) {
        log.severe("Получение адреса с id="+id);
        return addressesRepository.findById(id).get();
    }

    @Override
    public List<Addresses> getAllAddresses() {
        log.severe("Получение всех категорий");
        List<Addresses> listOfAddresses = new ArrayList<>();
        addressesRepository.findAll().forEach(e -> listOfAddresses.add(e));
        return listOfAddresses;
    }
}
