package com.company.storage.inmemory;

import com.company.domain.Author;
import com.company.domain.Book;
import com.company.storage.BookStorage;

public class BookStorageImpl implements BookStorage {
    private static Book[] array = new Book[10];
    private static int incId = 1;
    private static int size = 0;

    @Override
    public void save(Book book) {
        array[size++] = book;
        book.setId(incId++);
    }

    @Override
    public String updateTitleById(int id, String newTitle) {
        String old = null;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if (array[i].getId() == id) {
                old = array[i].getTitle();
                array[i].setTitle(newTitle);
                break;
            }
        }
        return old;
    }

    @Override
    public void updateDescriptionById(int id, String newDescription) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if (array[i].getId() == id) {
                array[i].setDescription(newDescription);
                break;
            }
        }
    }

    @Override
    public int updatePriceById(int id, int newPrice) {
        int old = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if (array[i].getId() == id) {
                old = array[i].getPrice();
                array[i].setPrice(newPrice);
                break;
            }
        }
        return old;
    }

    @Override
    public Author updateAuthor(int id, Author author) {
        Author old = null;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getId() == id)
                old = array[i].getAuthor();
                array[i].setAuthor(author);
        }
        return old;
    }

    @Override
    public Book deleteById(int id) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if (array[i].getId() == id) {
                Book old = array[i];
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
    public Book deleteByTitle(String title) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if (array[i].getTitle().equals(title)) {
                Book old = array[i];
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
    public void deleteAllByAuthor(Author author) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if (array[i].getAuthor().equals(author)) {
                for (int j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
            }
        }
    }

    @Override
    public Book getById(int id) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getId() == id){
                return array[i];
            }
        }
        return null;
    }

    @Override
    public Book getByTitle(String title) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getTitle().equals(title)){
                return array[i];
            }
        }
        return null;
    }

    @Override
    public Book[] getAll() {
        Book[] books = new Book[size];
        for (int i = 0; i < books.length; i++) {
            books[i] = array[i];
        }
        return books;
    }

    @Override
    public Book[] getAllByPrice(int price) {
        int sizePrice = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getPrice() == price) {
                sizePrice++;
            }
        }
        Book[] books = new Book[sizePrice];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getPrice() == price) {
                books[j++] = array[i];
            }
        }
        return books;
    }

    @Override
    public Book[] getAllByAuthor(Author author) {
        int sizeAuthor = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getAuthor().equals(author))
                sizeAuthor++;
        }
        Book[] books = new Book[sizeAuthor];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getAuthor().equals(author))
                books[j++] = array[i];
        }
        return books;
    }

    @Override
    public boolean contains(String title) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getTitle().equals(title)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].equals(book))
                return true;
        }
        return false;
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
    public boolean contains(Author author) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) break;
            if(array[i].getAuthor().equals(author))
                return true;
        }
        return false;
    }
}
