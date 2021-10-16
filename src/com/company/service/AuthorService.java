package com.company.service;

import com.company.domain.Author;

public interface AuthorService {
    boolean add(Author author);

    void updateDescriptionById(int id, String description);

    String updateNameById(int id, String name);

    Author deleteById(int id);

    Author deleteByName(String name);

    Author[] findAll();

    Author findById(int id);

    Author findByName(String name);
}
