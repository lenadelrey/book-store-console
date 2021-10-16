package com.company.console;

import com.company.console.action.*;
import com.company.domain.Book;
import com.company.domain.Role;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleWriter.writeString;

public class ConsoleApplication {
    public static Session session = null;

    private UserAction userAction = new ConsoleUserAction();
    private BookAction bookAction = new ConsoleBookAction();
    private OrderAction orderAction = new ConsoleOrderAction();
    private AuthorAction authorAction = new ConsoleAuthorAction();
    private AddressAction addressAction = new ConsoleAddressAction();
    private StoreAction storeAction = new ConsoleStoreAction();

    public void run() {
        while (true) {
            if (session == null) {
                writeString("Hello Guest");
                guestMenu();
                switch (readInt()) {
                    case 0:
                        return;
                    case 1:
                        userAction.registration();
                        break;
                    case 2:
                        userAction.authorization();
                        break;
                }
            } else if (session.getUser().getRole().equals(Role.USER)) {
                writeString("Hello, " + session.getUser().getName());
                userMenu();
                switch (readInt()) {
                    case 0:
                        userAction.logout();
                        continue;
                    case 1:
                        bookAction.findByTitle();
                        break;
                    case 2:
                        bookAction.findAllByAuthorForUser();
                        break;
                    case 3:
                        bookAction.findAllByPriceForUser();
                        break;
                    case 4:
                        bookAction.findAllForUser();
                        break;
                    case 5:
                        accountMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.updateNameForUser();
                                break;
                            case 2:
                                userAction.updatePasswordForUser();
                                break;
                            case 3:
                                userAction.updateAgeForUser();
                                break;
                        }
                        break;
                    case 6:
                        basketMenu();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                orderAction.deleteBook();
                                break;
                            case 2:
                                orderAction.addBook();
                                break;
                            case 3:
                                orderAction.save();
                                break;
                        }
                        break;
                    case 7:
                        orderMenuForUser();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                orderAction.getAll();
                                break;
                            case 2:
                                orderAction.updateAddressById();
                                break;
                            case 3:
                                orderAction.updateTypeById();
                                break;
                            case 4:
                                orderAction.addApplication();
                                break;
                            case 5:
                                orderAction.getAllByType();
                                break;
                        }
                }
            } else if (session.getUser().getRole().equals(Role.ADMIN)) {
                writeString("Hello, " + session.getUser().getName());
                adminMenu();
                switch (readInt()) {
                    case 0:
                        userAction.logout();
                        continue;
                    case 1:
                        userMenuForAdmin();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.updateLoginById();
                                break;
                            case 2:
                                userAction.updatePasswordById();
                                break;
                            case 3:
                                userAction.getById();
                                break;
                            case 4:
                                userAction.getAll();
                                break;
                            case 5:
                                userAction.getAllByName();
                                break;
                            case 6:
                                userAction.getByLogin();
                                break;
                            case 7:
                                userAction.getAllByAddress();
                                break;
                            case 8:
                                userAction.getAllByAge();
                                break;
                        }
                        break;
                    case 2:
                        bookMenuForAdmin();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                bookAction.add();
                                break;
                            case 2:
                                bookAction.updateTitleById();
                                break;
                            case 3:
                                bookAction.updateAuthor();
                                break;
                            case 4:
                                bookAction.updateDescriptionById();
                                break;
                            case 5:
                                bookAction.deleteById();
                                break;
                            case 6:
                                bookAction.deleteByTitle();
                                break;
                            case 7:
                                bookAction.deleteAllByAuthor();
                                break;
                            case 8:
                                bookAction.findAll();
                                break;
                            case 9:
                                bookAction.findByTitle();
                                break;
                            case 10:
                                bookAction.findById();
                                break;
                            case 11:
                                bookAction.findAllByPrice();
                                break;
                            case 12:
                                bookAction.findAllByAuthor();
                                break;
                        }
                        break;
                    case 3:
                        orderMenuForAdmin();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                orderAction.save();
                                break;
                            case 2:
                                orderAction.updateAddressById();
                                break;
                            case 3:
                                orderAction.updateTypeById();
                                break;
                            case 4:
                                orderAction.updateStatus();
                                break;
                            case 5:
                                orderAction.delete();
                                break;
                            case 6:
                                orderAction.deleteById();
                                break;
                            case 7:
                                orderAction.deleteBook();
                                break;
                            case 8:
                                orderAction.addBook();
                                break;
                            case 9:
                                orderAction.getById();
                                break;
                            case 10:
                                orderAction.getAll();
                                break;
                            case 11:
                                orderAction.getAllByUser();
                                break;
                            case 12:
                                orderAction.getAllByAddress();
                                break;
                            case 13:
                                orderAction.getAllByStatus();
                                break;
                            case 14:
                                orderAction.getAllByType();
                                break;
                            case 15:
                                orderAction.getAllByStore();
                                break;
                            case 16:
                                orderAction.applicationProcess();
                                break;
                        }
                        break;
                    case 4:
                        authorMenuForAdmin();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                authorAction.add();
                                break;
                            case 2:
                                authorAction.updateDescriptionById();
                                break;
                            case 3:
                                authorAction.updateNameById();
                                break;
                            case 4:
                                authorAction.deleteById();
                                break;
                            case 5:
                                authorAction.deleteByName();
                                break;
                            case 6:
                                authorAction.findAll();
                                break;
                            case 7:
                                authorAction.findById();
                                break;
                            case 8:
                                authorAction.findByName();
                                break;
                        }
                        break;
                    case 5:
                        addressMenuForAdmin();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                addressAction.add();
                                break;
                            case 2:
                                addressAction.updateStreetById();
                                break;
                            case 3:
                                addressAction.updateHomeById();
                                break;
                            case 4:
                                addressAction.deleteById();
                                break;
                            case 5:
                                addressAction.findAll();
                                break;
                            case 6:
                                addressAction.findAllByStreet();
                                break;
                            case 7:
                                addressAction.findAllByHome();
                                break;
                            case 8:
                                addressAction.findById();
                                break;
                        }
                        break;
                    case 6:
                        storeMenuForAdmin();
                        switch (readInt()) {
                            case 0:
                                continue;
                            case 1:
                                storeAction.add();
                                break;
                            case 2:
                                storeAction.updateName();
                                break;
                            case 3:
                                storeAction.updateAddressById();
                                break;
                            case 4:
                                storeAction.delete();
                                break;
                            case 5:
                                storeAction.getByName();
                                break;
                            case 6:
                                storeAction.getByAddress();
                                break;
                            case 7:
                                storeAction.getById();
                                break;
                            case 8:
                                storeAction.getAll();
                                break;
                        }
                        break;
                }
            }
        }
    }

    private void guestMenu() {
        writeString("0 - Exit");
        writeString("1 - Registration");
        writeString("2 - Authorization");
    }

    private void userMenu() {
        writeString("0 - Logout");
        writeString("1 - Find book by title");
        writeString("2 - Find book by author");
        writeString("3 - Find book by price");
        writeString("4 - Get all books");
        writeString("5 - Account");
        writeString("6 - Basket");
        writeString("7 - Orders");
    }

    private void printBasket() {
        Book[] books = session.getBasket().getBooks();
        for (Book book : books) {
            if (book == null) break;
            writeString("Book: " + book.getTitle());
        }
    }

    private void accountMenu() {
        writeString("0 - Back");
        writeString("1 - Update name");
        writeString("2 - Update password");
        writeString("3 - Update age");
    }

    private void basketMenu() {
        printBasket();
        writeString("0 - Back");
        writeString("1 - Delete book");
        writeString("2 - Add new book");
        writeString("3 - Create new order");
    }

    private void orderMenuForUser() {
        writeString("0 - Back");
        writeString("1 - Get all orders");
        writeString("2 - Update address");
        writeString("3 - Update type");
        writeString("4 - Add ticket to delete order");
        writeString("5 - Get all by type");
    }

    private void adminMenu() {
        writeString("0 - Logout");
        writeString("1 - User menu");
        writeString("2 - Book menu");
        writeString("3 - Order menu");
        writeString("4 - Author menu");
        writeString("5 - Address menu");
        writeString("6 - Store menu");
    }

    private void userMenuForAdmin() {
        writeString("0 - Back");
        writeString("1 - Update login");
        writeString("2 - Update password");
        writeString("3 - Get user by id");
        writeString("4 - Get all users");
        writeString("5 - Get all by name");
        writeString("6 - Get user by login");
        writeString("7 - Get all by address");
        writeString("8 - Get all by age");
    }

    private void bookMenuForAdmin() {
        writeString("0 - Back");
        writeString("1 - Add new book");
        writeString("2 - Update title");
        writeString("3 - Update author");
        writeString("4 - Update description");
        writeString("5 - Delete book by id");
        writeString("6 - Delete book by title");
        writeString("7 - Delete all by author");
        writeString("8 - Get all books");
        writeString("9 - Find book by title");
        writeString("10 - Find book by id");
        writeString("11 - Find all by price");
        writeString("12 - Find all by author");
    }

    private void orderMenuForAdmin() {
        writeString("0 - Back");
        writeString("1 - Add new order");
        writeString("2 - Update address");
        writeString("3 - Update type");
        writeString("4 - Update status");
        writeString("5 - Delete order");
        writeString("6 - Delete order by id");
        writeString("7 - Delete book");
        writeString("8 - Add book to order");
        writeString("9 - Get order by id");
        writeString("10 - Get all");
        writeString("11 - Get all by user");
        writeString("12 - Get all by address");
        writeString("13 - Get all by status");
        writeString("14 - Get all by type");
        writeString("15 - Get all by store");
        writeString("16 - Process application");

    }

    private void authorMenuForAdmin() {
        writeString("0 - Back");
        writeString("1 - Add new author");
        writeString("2 - Update description");
        writeString("3 - Update name");
        writeString("4 - Delete author by id");
        writeString("5 - Delete author by name");
        writeString("6 - Get all authors");
        writeString("7 - Find author by id");
        writeString("8 - Find author by name");
    }

    private void addressMenuForAdmin() {
        writeString("0 - Back");
        writeString("1 - Add new address");
        writeString("2 - Update street");
        writeString("3 - Update home");
        writeString("4 - Delete address");
        writeString("5 - Get all addresses");
        writeString("6 - Find all by street");
        writeString("7 - Find all by home");
        writeString("8 - Find address by id");
    }

    private void storeMenuForAdmin() {
        writeString("0 - Back");
        writeString("1 - Add new store");
        writeString("2 - Update name");
        writeString("3 - Update address");
        writeString("4 - Delete store");
        writeString("5 - Get store by name");
        writeString("6 - Get store by address");
        writeString("7 - Get store by id");
        writeString("8 - Get all");
    }
}
