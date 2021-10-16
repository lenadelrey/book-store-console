package com.company.storage;

import com.company.domain.Address;
import com.company.domain.User;

import java.io.IOException;

public interface UserStorage {
    void add(User user);

    User updateNameById(int id, String name);

    User updateLoginById(int id, String login);

    User updatePasswordById(int id, String password);

    User updateAgeById(int id, int age);

    User updateAddressById(int id, Address address);

    User findById(int id);

    User[] findAll();

    User[] findAllByName(String name);

    User findByLogin(String login);

    User[] findAllByAge(int age);

    User[] findAllByAddress(Address address);

    boolean contains(User user);

    boolean contains(String login);

    boolean contains(int id);

    boolean contains(Address address);
}
