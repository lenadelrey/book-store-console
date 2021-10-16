package com.company.storage.inmemory;

import com.company.domain.Author;
import com.company.storage.AuthorStorage;

import java.util.Arrays;

public class AuthorStorageImpl implements AuthorStorage {
    private static Author[] array = new Author[10];
    private static int incId = 1;
    private static int size = 0;

    @Override
    public void save(Author author) {
        array[size++] = author;
        author.setId(incId++);
    }

    @Override
    public void updateDescriptionById(int id, String description) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id) {
                array[i].setDescription(description);
                break;
            }
        }
    }

    @Override
    public String updateNameById(int id, String name) {
        String old = null;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id) {
                old = array[i].getName();
                array[i].setName(name);
                break;
            }
        }
        return old;
    }

    @Override
    public Author deleteById(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if (array[i].getId() == id) {
                Author old = array[i];
                for (int j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
                return old;
            }
        }
        return null;
    }

    @Override
    public Author deleteByName(String name) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if (array[i].getName().equals(name)) {
                Author old = array[i];
                for (int j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
                return old;
            }
        }
        return null;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public boolean contains(String name) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getName().equals(name))
                return true;
        }
        return false;
    }

    @Override
    public boolean contains(Author author) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].equals(author))
                return true;
        }
        return false;
    }

    @Override
    public Author[] findAll() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public Author findById(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id)
                return array[i];
        }
        return null;
    }

    @Override
    public Author findByName(String name) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getName().equals(name))
                return array[i];
        }
        return null;
    }
}
