package com.company.service;

import com.company.domain.*;
import com.company.storage.OrderStorage;
import com.company.storage.db.DbOrderStorage;
import com.company.storage.file.FileOrderStorage;
import com.company.storage.inmemory.OrderStorageImpl;

public class OrderServiceImpl implements OrderService {

    private OrderStorage orderStorage = new DbOrderStorage();

    @Override
    public boolean save(Order order) {
        if (orderStorage.contains(order)) {
            return false;
        }
        orderStorage.save(order);
        return true;
    }

    @Override
    public Address updateAddressById(int id, Address address) {
        if (orderStorage.contains(id)) {
            return orderStorage.updateAddressById(id, address);
        }
        return null;
    }

    @Override
    public Type updateTypeById(int id, Type type, Address address, Store store, boolean isDelivery) {
        if (orderStorage.contains(id)) {
            return orderStorage.updateTypeById(id, type, address, store, isDelivery);
        }
        return null;
    }

    @Override
    public Status updateStatus(int id, Status status) {
        if (orderStorage.contains(id)) {
            return orderStorage.updateStatus(id, status);
        }
        return null;
    }

    @Override
    public void addApplication(Order order, Application application) {
        if(orderStorage.contains(order)) {
            orderStorage.addApplication(order, application);
        }
    }

    @Override
    public void applicationProcess() {
        orderStorage.applicationProcess();
    }

    @Override
    public Order deleteById(int id) {
        if (orderStorage.contains(id)) {
            return orderStorage.deleteById(id);
        }
        return null;
    }

    @Override
    public Order delete(Order order) {
        if (orderStorage.contains(order)) {
            return orderStorage.delete(order);
        }
        return null;
    }

    @Override
    public Book deleteBook(int id, Book book) {
        if (orderStorage.contains(id)) {
            return orderStorage.deleteBook(id, book);
        }
        return null;
    }

    @Override
    public boolean addBook(int id, Book book) {
        if (orderStorage.contains(id)) {
            orderStorage.addBook(id, book);
            return true;
        }
        return false;
    }

    @Override
    public Order getById(int id) {
        if (orderStorage.contains(id)) {
            return orderStorage.getById(id);
        }
        return null;
    }

    @Override
    public Order[] getAll() {
        return orderStorage.getAll();
    }

    @Override
    public Order[] getAllByUser(User user) {
        if (orderStorage.contains(user)) {
            return orderStorage.getAllByUser(user);
        }
        return null;
    }

    @Override
    public Order[] getAllByAddress(Address address) {
        if (orderStorage.contains(address)) {
            return orderStorage.getAllByAddress(address);
        }
        return null;
    }

    @Override
    public Order[] getAllByType(Type type) {
        if (orderStorage.contains(type)) {
            return orderStorage.getAllByType(type);
        }
        return null;
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        if (orderStorage.contains(status)) {
            return orderStorage.getAllByStatus(status);
        }
        return null;
    }

    @Override
    public Order[] getAllByStore(Store store) {
        if (orderStorage.contains(store)) {
            return orderStorage.getAllByStore(store);
        }
        return null;
    }
}
