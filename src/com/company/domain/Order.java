package com.company.domain;

import java.util.Arrays;

public class Order {
    private int id;
    private Address address;
    private Store store;
    private User user;
    private boolean isDelivery;
    private Book[] books;
    private Type type;
    private Status status = Status.ACTIVE;
    private Application application = Application.DELETE;

    public Order() {
    }

    public Order(Address address, User user, Book[] books) {
        this.address = address;
        this.user = user;
        this.books = books;
        this.isDelivery = true;
        this.type = Type.DELIVERY;
    }

    public Order(Store store, User user, Book[] books) {
        this.store = store;
        this.user = user;
        this.books = books;
        this.type = Type.PICKUP;
    }

    public Order(int id, Address address, User user, Book[] books) {
        this.id = id;
        this.address = address;
        this.user = user;
        this.books = books;
        this.isDelivery = true;
        this.type = Type.DELIVERY;
    }

    public Order(int id, Store store, User user, Book[] books) {
        this.id = id;
        this.store = store;
        this.user = user;
        this.books = books;
        this.type = Type.PICKUP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address=" + address +
                ", store=" + store +
                ", user=" + user +
                ", isDelivery=" + isDelivery +
                ", books=" + Arrays.toString(books) +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
