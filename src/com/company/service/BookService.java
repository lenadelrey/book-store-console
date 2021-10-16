package com.company.service;

import com.company.domain.Author;
import com.company.domain.Book;

public interface BookService {
    boolean add(Book book);

    String updateTitleById(int id, String newTitle);

    void updateDescriptionById(int id, String newDescription);

    int updatePriceById(int id, int newPrice);

    Author updateAuthor(int id, Author author);

    Book deleteById(int id);

    Book deleteByTitle(String title);

    void deleteAllByAuthor(Author author);

    Book findById(int id);

    Book findByTitle(String title);

    Book[] findAll();

    Book[] findAllByPrice(int price);

    Book[] findAllByAuthor(Author author);
}
