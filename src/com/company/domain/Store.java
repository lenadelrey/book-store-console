package com.company.domain;

public class Store {
    private int id;
    private String name;
    private Address address;

    public Store() {
    }

    public Store(int id) {
        this.id = id;
    }

    public Store(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Store(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
