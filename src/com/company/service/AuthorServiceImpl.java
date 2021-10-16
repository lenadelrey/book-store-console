package com.company.service;

import com.company.domain.Author;
import com.company.storage.AuthorStorage;
import com.company.storage.db.DbAuthorStorage;
import com.company.storage.file.FileAuthorStorage;
import com.company.storage.inmemory.AuthorStorageImpl;

public class AuthorServiceImpl implements AuthorService {
    private AuthorStorage authorStorage = new DbAuthorStorage();

    @Override
    public boolean add(Author author) {
        if(authorStorage.contains(author)) {
            return false;
        }
        authorStorage.save(author);
        return true;
    }

    @Override
    public void updateDescriptionById(int id, String description) {
        if(authorStorage.contains(id)){
            authorStorage.updateDescriptionById(id, description);
        }
    }

    @Override
    public String updateNameById(int id, String name) {
        if(authorStorage.contains(id) && !authorStorage.contains(name)) {
            return authorStorage.updateNameById(id, name);
        }
        return null;
    }

    @Override
    public Author deleteById(int id) {
        if(authorStorage.contains(id)) {
            return authorStorage.deleteById(id);
        }
        return null;
    }

    @Override
    public Author deleteByName(String name) {
        if(authorStorage.contains(name)) {
            authorStorage.deleteByName(name);
        }
        return null;
    }

    @Override
    public Author[] findAll() {
        return authorStorage.findAll();
    }

    @Override
    public Author findById(int id) {
        if(authorStorage.contains(id)) {
            return authorStorage.findById(id);
        }
        return null;
    }

    @Override
    public Author findByName(String name) {
        if(authorStorage.contains(name)) {
            return authorStorage.findByName(name);
        }
        return null;
    }

}
