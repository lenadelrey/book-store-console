package com.company.storage;

import com.company.domain.*;

public interface OrderStorage {
    void save(Order order);

    void addApplication(Order order, Application application);

    void applicationProcess();

    Address updateAddressById(int id, Address address);

    Type updateTypeById(int id, Type type, Address address, Store store, boolean isDelivery);

    Status updateStatus(int id, Status status);

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

    boolean contains(int id);

    boolean contains(Order order);

    boolean contains(User user);

    boolean contains(Address address);

    boolean contains(Type type);

    boolean contains(Status status);

    boolean contains(Store store);
}
