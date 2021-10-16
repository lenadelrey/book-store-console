package com.company.domain;

import java.util.Arrays;

public class Basket {
    private Book[] books = new Book[10];
    private int size = 0;

    public void addBook(Book book){
        books[size++] = book;
    }

    public void delete(Book book){
        for (int i = 0; i < books.length; i++) {
            if(books[i] == book) {
                for (int j = 0; j < books.length - 1; j++) {
                    books[j] = books[j + 1];
                }
                size--;
            }
        }
    }

    public boolean contains(Book book){
        for (int i = 0; i < books.length; i++) {
            if(books[i] == null) break;
            if(books[i] == book) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public Book[] getBooks() {
        Book[] allBooks = new Book[size];
        for (int i = 0; i < size; i++) {
            allBooks[i] = books[i];
        }
        return allBooks;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "books=" + Arrays.toString(books) +
                '}';
    }
}
