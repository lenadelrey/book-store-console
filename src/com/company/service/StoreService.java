package com.company.service;

import com.company.domain.Address;
import com.company.domain.Store;

public interface StoreService {

    boolean add(Store store);

    Address updateAddressById(int id, Address address);

    String updateName(int id, String name);

    void delete(int id);

    Store getById(int id);

    Store getByName(String name);

    Store getByAddress(Address address);

    Store[] getAll();

}
