package com.company.service;

import com.company.domain.Address;
import com.company.domain.User;
import com.company.storage.UserStorage;
import com.company.storage.db.DbUserStorage;
import com.company.storage.file.FileUserStorage;
import com.company.storage.inmemory.UserStorageImpl;

public class UserServiceImpl implements UserService {
    private UserStorage userStorage = new DbUserStorage();

    @Override
    public boolean add(User user) {
        if(userStorage.contains(user)) {
            return false;
        }
        userStorage.add(user);
        return true;
    }

    @Override
    public User updateNameById(int id, String name) {
        if(userStorage.contains(id)){
            return userStorage.updateNameById(id, name);
        }
        return null;
    }

    @Override
    public User updateLoginById(int id, String login) {
        if(userStorage.contains(id)){
            return userStorage.updateLoginById(id, login);
        }
        return null;
    }

    @Override
    public User updatePasswordById(int id, String password) {
        if(userStorage.contains(id)){
            return userStorage.updatePasswordById(id, password);
        }
        return null;
    }

    @Override
    public User updateAgeById(int id, int age) {
        if(userStorage.contains(id)){
            return userStorage.updateAgeById(id, age);
        }
        return null;
    }

    @Override
    public User updateAddressById(int id, Address address) {
        if(userStorage.contains(id)){
            return userStorage.updateAddressById(id, address);
        }
        return null;
    }

    @Override
    public User getById(int id) {
        if(userStorage.contains(id)) {
            return userStorage.findById(id);
        }
        return null;
    }

    @Override
    public User[] getAll() {
        return userStorage.findAll();
    }

    @Override
    public User[] getAllByName(String name) {
        return userStorage.findAllByName(name);
    }

    @Override
    public User getByLogin(String login) {
        if(userStorage.contains(login)) {
            return userStorage.findByLogin(login);
        }
        return null;
    }

    @Override
    public User[] getAllByAge(int age) {
        return userStorage.findAllByAge(age);
    }

    @Override
    public User[] getAllByAddress(Address address) {
        if(userStorage.contains(address)) {
            return userStorage.findAllByAddress(address);
        }
        return null;
    }
}
