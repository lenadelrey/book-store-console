package com.company.console.action;

import com.company.console.ConsoleApplication;
import com.company.domain.Author;
import com.company.domain.Basket;
import com.company.domain.Book;
import com.company.service.AuthorService;
import com.company.service.AuthorServiceImpl;
import com.company.service.BookService;
import com.company.service.BookServiceImpl;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.*;
import static com.company.console.validator.AbstractValidator.validId;
import static com.company.console.validator.AuthorValidator.validName;
import static com.company.console.validator.BookValidator.*;

public class ConsoleBookAction implements BookAction {
    private BookService bookService = new BookServiceImpl();
    private AuthorService authorService = new AuthorServiceImpl();

    @Override
    public void add() {
        writeString("Enter title");
        String title = readString();

        if (!validTitle(title)) {
            writeString("Invalid title");
            return;
        }

        writeString("Enter description");
        String description = readString();

        if (!validDescription(description)) {
            writeString("Invalid description");
            return;
        }

        writeString("Enter price");
        int price = readInt();

        if (!validPrice(price)) {
            writeString("Invalid price");
            return;
        }

        Author[] all = authorService.findAll();

        if (all.length == 0) {
            writeString("Authors empty");
            return;
        }

        writeString("Choose author");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Author " + all[i].getName());
        }
        int i = readInt() - 1;
        Author author = all[i];
        bookService.add(new Book(title, description, author, price));
        writeString("Successfully added");
    }

    @Override
    public void updateTitleById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Enter new title");
        String title = readString();

        if (!validTitle(title)) {
            writeString("Invalid title");
            return;
        }

        bookService.updateTitleById(id, title);
        writeString("Successfully updated");
    }

    @Override
    public void updateDescriptionById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Enter new description");
        String description = readString();

        if (!validDescription(description)) {
            writeString("Invalid description");
            return;
        }

        bookService.updateDescriptionById(id, description);
        writeString("Successfully updated");
    }

    @Override
    public void updatePriceById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Enter new price");
        int price = readInt();

        if (!validPrice(price)) {
            writeString("Invalid price");
            return;
        }

        bookService.updatePriceById(id, price);
        writeString("Successfully updated");
    }

    @Override
    public void updateAuthor() {
        writeString("Enter book id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        Author[] all = authorService.findAll();

        if (all.length == 0) {
            writeString("Authors empty");
            return;
        }

        writeString("Choose author");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Author " + all[i].getName());
        }
        int i = readInt() - 1;
        Author author = all[i];

        bookService.updateAuthor(id, author);
        writeString("Successfully updated");
    }

    @Override
    public void deleteById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        bookService.deleteById(id);
        writeString("Successfully deleted");
    }

    @Override
    public void deleteByTitle() {
        writeString("Enter new title");
        String title = readString();

        if (!validTitle(title)) {
            writeString("Invalid title");
            return;
        }

        bookService.deleteByTitle(title);
        writeString("Successfully deleted");
    }

    @Override
    public void deleteAllByAuthor() {
        Author[] all = authorService.findAll();

        if (all.length == 0) {
            writeString("Authors empty");
            return;
        }

        writeString("Choose author");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Author " + all[i].getName());
        }
        int i = readInt() - 1;
        Author author = all[i];
        bookService.deleteAllByAuthor(author);
        writeString("Successfully deleted");
    }

    @Override
    public void findById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeObject(bookService.findById(id));
    }

    @Override
    public void findByTitle() {
        writeString("Enter title");
        String title = readString();

        if (!validTitle(title)) {
            writeString("Invalid title");
            return;
        }

        Book byTitle = bookService.findByTitle(title);
        writeString("Book: " + byTitle.getTitle());
        writeString("1 - Open, 2 - Back");
        if (readInt() == 1) {
            writeString("Book: " + byTitle.getTitle() + " /nAuthor: " + byTitle.getAuthor());
            writeString("1 - Add to basket, 2 - Back");
            if (readInt() == 1) {
                Basket basket = ConsoleApplication.session.getBasket();
                basket.addBook(byTitle);
                writeString("Successfully added");
            }
        }
    }

    @Override
    public void findAll() {
        if (bookService.findAll() == null) {
            writeString("Books empty");
        } else {
            writeObjects(bookService.findAll());
        }
    }

    @Override
    public void findAllForUser() {
        Book[] all = bookService.findAll();
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Book: " + all[i].getTitle());
        }
        writeString("Choose book");
        int i = readInt() - 1;
        Book book = all[i];
        writeString("Book: " + book.getTitle() + " /nAuthor: " + book.getAuthor().getName());
        writeString("1 - Add to basket, 2 - back");
        if (readInt() == 1) {
            ConsoleApplication.session.getBasket().addBook(book);
            writeString("Successfully added");
        }
    }

    @Override
    public void findAllByPrice() {
        writeString("Enter price");
        int price = readInt();

        if (!validPrice(price)) {
            writeString("Invalid price");
            return;
        }

        if (bookService.findAllByPrice(price) == null) {
            writeString("No books with this price");
        } else {
            writeObjects(bookService.findAllByPrice(price));
        }
    }

    @Override
    public void findAllByPriceForUser() {
        writeString("Enter price");
        int price = readInt();

        if (!validPrice(price)) {
            writeString("Invalid price");
            return;
        }
        if (bookService.findAllByPrice(price) == null) {
            writeString("No books with this price");
        }
        Book[] all = bookService.findAll();
        for (int i = 0; i < all.length; i++) {
            if (all[i].getPrice() == price) {
                writeString((i + 1) + " Book: " + all[i].getTitle());
            }
        }

        writeString("Choose a book");
        int i = readInt() - 1;
        Book book = all[i];
        writeString("Book: " + book.getTitle() + " /nAuthor: " + book.getAuthor().getName());
        writeString("1 - Add to basket, 2 - back");
        if (readInt() == 1) {
            ConsoleApplication.session.getBasket().addBook(book);
            writeString("Successfully added");
        }

    }

    @Override
    public void findAllByAuthor() {
        Author[] all = authorService.findAll();

        if (all.length == 0) {
            writeString("Authors empty");
            return;
        }

        writeString("Choose author");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Author " + all[i].getName());
        }
        int i = readInt() - 1;
        Author author = all[i];
        writeObjects(bookService.findAllByAuthor(author));
    }

    @Override
    public void findAllByAuthorForUser() {
        Author[] all = authorService.findAll();

        if (all.length == 0) {
            writeString("Authors empty");
            return;
        }

        writeString("Choose author");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " " + all[i].getName());
        }
        int i = readInt() - 1;
        Author author = all[i];

        Book[] books = bookService.findAll();
        for (int i1 = 0; i1 < books.length; i1++) {
            if (books[i1].getAuthor().equals(author)) {
                writeString((i + 1) + " " + books[i].getTitle());
            }
        }

        writeString("Choose book");
        int i2 = readInt() - 1;
        Book book = books[i2];
        writeString("Book: " + book.getTitle() + " \nAuthor: " + book.getAuthor().getName());
        writeString("1 - Add to basket, 2 - back");
        if (readInt() == 1) {
            ConsoleApplication.session.getBasket().addBook(book);
            writeString("Successfully added");
        }
    }
}
