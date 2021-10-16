package com.company.service;

import com.company.domain.Address;

public interface AddressService {
    boolean add(Address address);

    String updateStreetById(int id, String street);

    int updateHomeById(int id, int home);

    Address deleteById(int id);

    Address[] findAllByStreet(String street);

    Address[] findAllByHome(int home);

    Address[] findAll();

    Address findById(int id);
}
