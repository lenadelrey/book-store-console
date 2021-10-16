package com.company.console.action;

public interface UserAction {

    void add();

    void registration();

    void authorization();

    void logout();

    void updateNameById();

    void updateNameForUser();

    void updateLoginById();

    void updatePasswordForUser();

    void updatePasswordById();

    void updateAgeById();

    void updateAgeForUser();

    void updateAddressById();

    void getById();

    void getAll();

    void getAllByName();

    void getByLogin();

    void getAllByAge();

    void getAllByAddress();
}
