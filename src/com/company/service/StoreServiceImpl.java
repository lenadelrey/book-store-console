package com.company.service;

import com.company.domain.Address;
import com.company.domain.Store;
import com.company.storage.StoreStorage;
import com.company.storage.db.DbStoreStorage;
import com.company.storage.file.FileStoreStorage;
import com.company.storage.inmemory.StoreStorageImpl;

public class StoreServiceImpl implements StoreService {
    private StoreStorage storeStorage = new DbStoreStorage();

    @Override
    public boolean add(Store store) {
        if (storeStorage.contains(store)) {
            return false;
        }
        storeStorage.save(store);
        return true;
    }

    @Override
    public Address updateAddressById(int id, Address address) {
        if(storeStorage.contains(id)) {
            return storeStorage.updateAddressById(id, address);
        }
        return null;
    }

    @Override
    public String updateName(int id, String name) {
        if(storeStorage.contains(id)) {
            return storeStorage.updateName(id, name);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        if(storeStorage.contains(id)) {
            storeStorage.delete(id);
        }
    }

    @Override
    public Store getById(int id) {
        if(storeStorage.contains(id)) {
            return storeStorage.getById(id);
        }
        return null;
    }

    @Override
    public Store getByName(String name) {
        if(storeStorage.contains(name)) {
            return storeStorage.getByName(name);
        }
        return null;
    }

    @Override
    public Store getByAddress(Address address) {
        if(storeStorage.contains(address)) {
            return storeStorage.getByAddress(address);
        }
        return null;
    }

    @Override
    public Store[] getAll() {
        return storeStorage.getAll();
    }
}
