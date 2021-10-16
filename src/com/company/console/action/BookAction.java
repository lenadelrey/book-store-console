package com.company.console.action;

public interface BookAction {
    void add();

    void updateTitleById();

    void updateDescriptionById();

    void updatePriceById();

    void updateAuthor();

    void deleteById();

    void deleteByTitle();

    void deleteAllByAuthor();

    void findById();

    void findByTitle();

    void findAll();

    void findAllForUser();

    void findAllByPrice();

    void findAllByPriceForUser();

    void findAllByAuthor();

    void findAllByAuthorForUser();
}
