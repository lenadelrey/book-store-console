package com.company.storage.inmemory;

import com.company.domain.Address;
import com.company.storage.AddressStorage;

import java.util.Arrays;

public class AddressStorageImpl implements AddressStorage {
    private static Address[] array = new Address[10];
    private static int incId = 1;
    private static int size = 0;

    @Override
    public void save(Address address) {
        array[size++] = address;
        address.setId(incId++);
    }

    @Override
    public String updateStreetById(int id, String street) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            String old = array[i].getStreet();
            if(array[i].getId() == id) {
                array[i].setStreet(street);
                return old;
            }
        }
        return null;
    }

    @Override
    public int updateHomeById(int id, int home) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            int old = array[i].getHome();
            if(array[i].getId() == id) {
                array[i].setHome(home);
                return old;
            }
        }
        return 0;
    }

    @Override
    public Address deleteById(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id) {
                Address old = array[i];
                for (int j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
                return old;
            }
        }
        return null;
    }

    @Override
    public Address[] findAllByStreet(String street) {
        int sizeAddress = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getStreet().equals(street))
                sizeAddress++;
        }
        Address[] addresses = new Address[sizeAddress];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getStreet().equals(street))
                addresses[j++] = array[i];
        }
        return addresses;
    }

    @Override
    public Address[] findAllByHome(int home) {
        int sizeAddress = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getHome() == home)
                sizeAddress++;
        }
        Address[] addresses = new Address[sizeAddress];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getHome() == home)
                addresses[j++] = array[i];
        }
        return addresses;
    }

    @Override
    public Address[] findAll() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public Address findById(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public boolean contains(String street, int home) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getHome() == home && array[i].getStreet().equals(street))
                return true;
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public boolean contains(String street) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getStreet().equals(street))
                return true;
        }
        return false;
    }
}
