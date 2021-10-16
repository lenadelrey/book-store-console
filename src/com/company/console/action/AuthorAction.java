package com.company.console.action;

public interface AuthorAction {
    void add();

    void updateDescriptionById();

    void updateNameById();

    void deleteById();

    void deleteByName();

    void findAll();

    void findById();

    void findByName();
}
