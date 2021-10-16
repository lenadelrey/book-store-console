package com.company.storage;

import com.company.domain.Address;

import java.io.IOException;

public interface AddressStorage {
    void save(Address address);

    String updateStreetById(int id, String street);

    int updateHomeById(int id, int home);

    Address deleteById(int id);

    Address[] findAllByStreet(String street);

    Address[] findAllByHome(int home);

    Address[] findAll();

    Address findById(int id);

    boolean contains(String street, int home);

    boolean contains(int id);

    boolean contains(String street);
}
