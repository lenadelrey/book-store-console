package com.company.storage;

import com.company.domain.Address;
import com.company.domain.Store;

import java.io.IOException;

public interface StoreStorage {

    void save(Store store);

    Address updateAddressById(int id, Address address);

    String updateName(int id, String name);

    void delete(int id);

    Store getById(int id);

    Store getByName(String name);

    Store getByAddress(Address address);

    Store[] getAll();

    boolean contains(int id);

    boolean contains(Address address);

    boolean contains(Store store);

    boolean contains(String name);
}
