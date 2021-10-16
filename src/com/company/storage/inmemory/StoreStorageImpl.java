package com.company.storage.inmemory;

import com.company.domain.Address;
import com.company.domain.Store;
import com.company.storage.StoreStorage;

public class StoreStorageImpl implements StoreStorage {

    private static Store[] array = new Store[10];
    private static int incId = 1;
    private static int size = 0;

    @Override
    public void save(Store store) {
        array[size++] = store;
        store.setId(incId++);
    }

    @Override
    public Address updateAddressById(int id, Address address) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            Address old = array[i].getAddress();
            if (array[i].getId() == id) {
                array[i].setAddress(address);
                return old;
            }
        }
        return null;
    }

    @Override
    public String updateName(int id, String name) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            String old = array[i].getName();
            if (array[i].getId() == id) {
                array[i].setName(name);
                return old;
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i].getId() == id) {
                for (int j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
            }
        }
    }

    @Override
    public Store getById(int id) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i].getId() == id) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public Store getByName(String name) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i].getName().equals(name)) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public Store getByAddress(Address address) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getAddress().equals(address)) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public Store[] getAll() {
        Store[] stores = new Store[size];
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            stores[i] = array[i];
        }
        return stores;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i].getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i].getAddress().equals(address))
                return true;
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i] == store)
                return true;
        }
        return false;
    }

    @Override
    public boolean contains(String name) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i].getName().equals(name))
                return true;
        }
        return false;
    }
}
