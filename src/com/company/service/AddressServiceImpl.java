package com.company.service;

import com.company.domain.Address;
import com.company.storage.AddressStorage;
import com.company.storage.db.DbAddressStorage;
import com.company.storage.file.FileAddressStorage;
import com.company.storage.inmemory.AddressStorageImpl;

import java.util.Arrays;

public class AddressServiceImpl implements AddressService {
    private AddressStorage addressStorage = new DbAddressStorage();

    @Override
    public boolean add(Address address) {
        if (addressStorage.contains(address.getStreet(), address.getHome())) {
            return false;
        }
        addressStorage.save(address);
        return true;
    }

    @Override
    public String updateStreetById(int id, String street) {
        if (addressStorage.contains(id)) {
            return addressStorage.updateStreetById(id, street);
        }
        return null;
    }

    @Override
    public int updateHomeById(int id, int home) {
        if (addressStorage.contains(id)) {
            return addressStorage.updateHomeById(id, home);
        }
        return 0;
    }

    @Override
    public Address deleteById(int id) {
        if (addressStorage.contains(id)) {
            return addressStorage.deleteById(id);
        }
        return null;
    }

    @Override
    public Address[] findAllByStreet(String street) {
        if (addressStorage.contains(street)) {
            addressStorage.findAllByStreet(street);
        }
        return new Address[0];
    }

    @Override
    public Address[] findAllByHome(int home) {
        addressStorage.findAllByHome(home);
        return new Address[0];
    }

    @Override
    public Address[] findAll() {
        return addressStorage.findAll();
    }

    @Override
    public Address findById(int id) {
        if (addressStorage.contains(id)) {
            return addressStorage.findById(id);
        }
        return null;
    }
}
