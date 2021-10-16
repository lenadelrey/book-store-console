package com.company.service;

import com.company.domain.Address;
import com.company.domain.User;

public interface UserService {
    boolean add(User user);

    User updateNameById(int id, String name);

    User updateLoginById(int id, String login);

    User updatePasswordById(int id, String password);

    User updateAgeById(int id, int age);

    User updateAddressById(int id, Address address);

    User getById(int id);

    User[] getAll();

    User[] getAllByName(String name);

    User getByLogin(String login);

    User[] getAllByAge(int age);

    User[] getAllByAddress(Address address);

}
