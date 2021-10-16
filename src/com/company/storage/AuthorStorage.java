package com.company.storage;

import com.company.domain.Author;

import java.io.IOException;

public interface AuthorStorage {
    void save(Author author);

    void updateDescriptionById(int id, String description);

    String updateNameById(int id, String name);

    Author deleteById(int id);

    Author deleteByName(String name);

    boolean contains(int id);

    boolean contains(String name);

    boolean contains(Author author);

    Author[] findAll();

    Author findById(int id);

    Author findByName(String name);
}
