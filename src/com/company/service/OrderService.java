package com.company.service;

import com.company.domain.*;

public interface OrderService {
    boolean save(Order order);

    Address updateAddressById(int id, Address address);

    Type updateTypeById(int id, Type type, Address address, Store store, boolean isDelivery);

    Status updateStatus(int id, Status status);

    void addApplication(Order order, Application application);

    void applicationProcess();

    Order deleteById(int id);

    Order delete(Order order);

    Book deleteBook(int id, Book book);

    boolean addBook(int id, Book book);

    Order getById(int id);

    Order[] getAll();

    Order[] getAllByUser(User user);

    Order[] getAllByAddress(Address address);

    Order[] getAllByType(Type type);

    Order[] getAllByStatus(Status status);

    Order[] getAllByStore(Store store);
}
