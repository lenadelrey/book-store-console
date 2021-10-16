package com.company.service;

import com.company.domain.Author;
import com.company.domain.Book;
import com.company.storage.BookStorage;
import com.company.storage.db.DbBookStorage;
import com.company.storage.file.FileBookStorage;
import com.company.storage.inmemory.BookStorageImpl;

public class BookServiceImpl implements BookService {

    private BookStorage bookStorage = new DbBookStorage();

    @Override
    public boolean add(Book book) {
        if (bookStorage.contains(book)) {
            return false;
        }
        bookStorage.save(book);
        return true;
    }

    @Override
    public String updateTitleById(int id, String newTitle) {
        if (bookStorage.contains(id) && !bookStorage.contains(newTitle)) {
            return bookStorage.updateTitleById(id, newTitle);
        }
        return null;
    }

    @Override
    public void updateDescriptionById(int id, String newDescription) {
        if (bookStorage.contains(id)) {
            bookStorage.updateDescriptionById(id, newDescription);
        }
    }

    @Override
    public int updatePriceById(int id, int newPrice) {
        if (bookStorage.contains(id)) {
            return bookStorage.updatePriceById(id, newPrice);
        }
        return 0;
    }

    @Override
    public Author updateAuthor(int id, Author author) {
        if(bookStorage.contains(id)) {
            return bookStorage.updateAuthor(id, author);
        }
        return null;
    }

    @Override
    public Book deleteById(int id) {
        if (bookStorage.contains(id)) {
            return bookStorage.deleteById(id);
        }
        return null;
    }

    @Override
    public Book deleteByTitle(String title) {
        if (bookStorage.contains(title)) {
            return bookStorage.deleteByTitle(title);
        }
        return null;
    }

    @Override
    public void deleteAllByAuthor(Author author) {
        if (bookStorage.contains(author)) {
            bookStorage.deleteAllByAuthor(author);
        }
    }

    @Override
    public Book findById(int id) {
        if (bookStorage.contains(id)) {
            return bookStorage.getById(id);
        }
        return null;
    }

    @Override
    public Book findByTitle(String title) {
        if (bookStorage.contains(title)) {
            return bookStorage.getByTitle(title);
        }
        return null;
    }

    @Override
    public Book[] findAll() {
        return bookStorage.getAll();
    }

    @Override
    public Book[] findAllByPrice(int price) {
        return bookStorage.getAllByPrice(price);
    }

    @Override
    public Book[] findAllByAuthor(Author author) {
        return bookStorage.getAllByAuthor(author);
    }
}
