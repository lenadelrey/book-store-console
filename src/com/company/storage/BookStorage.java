package com.company.storage;

import com.company.domain.Author;
import com.company.domain.Book;

import java.io.IOException;

public interface BookStorage {
    void save(Book book);

    String updateTitleById(int id, String newTitle);

    void updateDescriptionById(int id, String newDescription);

    int updatePriceById(int id, int newPrice);

    Author updateAuthor(int id, Author author);

    Book deleteById(int id);

    Book deleteByTitle(String title);

    void deleteAllByAuthor(Author author);

    Book getById(int id);

    Book getByTitle(String title);

    Book[] getAll();

    Book[] getAllByPrice(int price);

    Book[] getAllByAuthor(Author author);

    boolean contains(String title);

    boolean contains(Book book);

    boolean contains(int id);

    boolean contains(Author author);
}
