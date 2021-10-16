package com.company.storage.inmemory;

import com.company.domain.Address;
import com.company.domain.Role;
import com.company.domain.User;
import com.company.storage.UserStorage;

import java.util.Arrays;

public class UserStorageImpl implements UserStorage {

    private static User[] array = new User[10];
    private static int incId = 1;
    private static int size = 0;

    static  {
        array[0] = new User(1, "Admin", "admin", "admin", 22, Role.ADMIN);
        array[1] = new User(2, "User", "user", "user", 22, Role.USER);
    }

    @Override
    public void add(User user) {
        array[size++] = user;
        user.setId(incId++);
    }

    @Override
    public User updateNameById(int id, String name) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            User old = array[i];
            if(array[i].getId() == id) {
                array[i].setName(name);
                return old;
            }
        }
        return null;
    }

    @Override
    public User updateLoginById(int id, String login) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            User old = array[i];
            if(array[i].getId() == id) {
                array[i].setLogin(login);
                return old;
            }
        }
        return null;
    }

    @Override
    public User updatePasswordById(int id, String password) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            User old = array[i];
            if(array[i].getId() == id) {
                array[i].setPassword(password);
                return old;
            }
        }
        return null;
    }

    @Override
    public User updateAgeById(int id, int age) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            User old = array[i];
            if(array[i].getId() == id) {
                array[i].setAge(age);
                return old;
            }
        }
        return null;
    }

    @Override
    public User updateAddressById(int id, Address address) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            User old = array[i];
            if(array[i].getId() == id) {
                array[i].setAddress(address);
                return old;
            }
        }
        return null;
    }

    @Override
    public User findById(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public User[] findAll() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public User[] findAllByName(String name) {
        int sizeName = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getName().equals(name)) {
                sizeName++;
            }
        }
        User[] users = new User[sizeName];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getName().equals(name)) {
                users[j++] = array[i];
            }
        }
        return users;
    }

    @Override
    public User findByLogin(String login) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getLogin().equals(login)) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public User[] findAllByAge(int age) {
        int sizeName = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getAge() == age) {
                sizeName++;
            }
        }
        User[] users = new User[sizeName];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getAge() == age) {
                users[j++] = array[i];
            }
        }
        return users;
    }

    @Override
    public User[] findAllByAddress(Address address) {
        int sizeName = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getAddress().equals(address)) {
                sizeName++;
            }
        }
        User[] users = new User[sizeName];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getAddress().equals(address)) {
                users[j++] = array[i];
            }
        }
        return users;
    }

    @Override
    public boolean contains(User user) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i] == user) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(String login) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }
}
