package com.company.console.action;

public interface AddressAction {
    void add();

    void updateStreetById();

    void updateHomeById();

    void deleteById();

    void findAllByStreet();

    void findAllByHome();

    void findAll();

    void findById();
}
