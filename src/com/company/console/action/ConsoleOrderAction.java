package com.company.console.action;

import com.company.console.ConsoleApplication;
import com.company.domain.*;
import com.company.service.OrderService;
import com.company.service.OrderServiceImpl;
import com.company.storage.*;
import com.company.storage.db.*;
import com.company.storage.inmemory.*;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleWriter.*;
import static com.company.console.validator.AbstractValidator.validId;

public class ConsoleOrderAction implements OrderAction {
    private OrderService orderService = new OrderServiceImpl();
    private AddressStorage addressStorage = new DbAddressStorage();
    private UserStorage userStorage = new DbUserStorage();
    private StoreStorage storeStorage = new DbStoreStorage();
    private OrderStorage orderStorage = new DbOrderStorage();
    private BookStorage bookStorage = new DbBookStorage();

    @Override
    public void save() {
        writeString("Choose the type: 1 - delivery or 2 - pickup");
        int option = readInt();

        if (option == 1) {
            Address[] all = addressStorage.findAll();

            if (all.length == 0) {
                writeString("Addresses empty");
                return;
            }

            writeString("Choose address");
            for (int i = 0; i < all.length; i++) {
                writeString((i + 1) + " Address " + all[i].getStreet() + " " + all[i].getHome());
            }
            int i = readInt() - 1;
            Address address = all[i];

            orderService.save(new Order(address, getUser(), saveBooks()));
            writeString("Successfully!");
        } else if (option == 2) {

            Store[] stores = storeStorage.getAll();

            if (stores.length == 0) {
                writeString("Stores empty");
                return;
            }
            writeString("Choose store");
            for (int i = 0; i < stores.length; i++) {
                writeString((i + 1) + " User " + stores[i].getName() + " " + stores[i].getAddress());
            }
            int i = readInt() - 1;
            Store store = stores[i];

            orderService.save(new Order(store, getUser(), saveBooks()));
            writeString("Successfully!");
        } else {
            writeString("Invalid option");
        }
    }

    private User getUser() {
        return ConsoleApplication.session.getUser();
    }

    private Book[] saveBooks() {
        Basket basket = ConsoleApplication.session.getBasket();
        return basket.getBooks();
    }

    @Override
    public void updateAddressById() {

        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        Address[] all = addressStorage.findAll();

        if (all.length == 0) {
            writeString("Addresses empty");
            return;
        }

        writeString("Choose address");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Address " + all[i].getStreet() + " " + all[i].getHome());
        }
        int i = readInt() - 1;
        Address address = all[i];

        orderService.updateAddressById(id, address);
        writeString("Successfully updated");
    }

    @Override
    public void updateTypeById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Choose type");
        Type[] values = Type.values();
        for (int i = 0; i < values.length; i++) {
            Type value = values[i];
            writeString((i + 1) + " Type " + value.name());
        }
        int i2 = readInt() - 1;

        if (i2 == 0) {
            Store store = null;
            Address[] all = addressStorage.findAll();

            if (all.length == 0) {
                writeString("Addresses empty");
                return;
            }

            writeString("Choose address");
            for (int i = 0; i < all.length; i++) {
                writeString((i + 1) + " Address " + all[i].getStreet() + " " + all[i].getHome());
            }
            int i1 = readInt() - 1;
            Address address = all[i1];
            orderStorage.updateTypeById(id, values[i2], address, store, true);
        } else if(i2 == 1) {
            Address address = null;

            Store[] stores = storeStorage.getAll();

            if (stores.length == 0) {
                writeString("Stores empty");
                return;
            }
            writeString("Choose store");
            for (int i = 0; i < stores.length; i++) {
                writeString((i + 1) + " User " + stores[i].getName() + " " + stores[i].getAddress());
            }
            int i = readInt() - 1;
            Store store = stores[i];
            orderStorage.updateTypeById(id, values[i2], address, store, false);
        }
        writeString("Successfully updated");
    }

    @Override
    public void updateStatus() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Choose status");
        Status[] values = Status.values();
        for (int i = 0; i < values.length; i++) {
            Status value = values[i];
            writeString((i + 1) + " Status " + value.name());
        }
        int i = readInt() - 1;

        orderStorage.updateStatus(id, values[i]);
        writeString("Successfully updated");
    }

    @Override
    public void addApplication() {
        writeString("Choose order");
        Order[] orders = orderStorage.getAllByUser(ConsoleApplication.session.getUser());

        if(orders.length == 0) {
            writeString("Orders empty");
            return;
        }

        for (int i = 0; i < orders.length; i++) {
            writeString((i + 1) + " Order " + orders[i].getType());
        }
        int i = readInt() - 1;
        orderStorage.addApplication(orders[i], orders[i].getApplication());
        writeString("The application will be processed");
    }

    @Override
    public void applicationProcess() {
        orderStorage.applicationProcess();
    }

    @Override
    public void deleteById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        orderService.deleteById(id);
        writeString("Successfully deleted");
    }

    @Override
    public void delete() {
        Order[] all = orderStorage.getAll();

        if (all.length == 0) {
            writeString("Orders empty");
            return;
        }

        writeString("Choose order");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Order " + all[i].getUser() + " " + all[i].getType());
        }
        int i = readInt() - 1;
        Order order = all[i];

        orderService.delete(order);
        writeString("Successfully deleted");
    }

    @Override
    public void deleteBook() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }
        Basket basket = ConsoleApplication.session.getBasket();
        Book[] books = basket.getBooks();

        if (books.length == 0) {
            writeString("Addresses empty");
            return;
        }

        writeString("Choose book");
        for (int i = 0; i < books.length; i++) {
            writeString((i + 1) + " Book " + books[i].getTitle() + " " + books[i].getAuthor());
        }
        int i = readInt() - 1;
        Book book = books[i];

        orderService.deleteBook(id, book);
        writeString("Successfully deleted");
    }

    @Override
    public void addBook() {
        Order[] all = orderService.getAll();

        if(all.length == 0) {
            writeString("Orders empty");
            return;
        }

        writeString("Choose order");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Order " + all[i].getId() + all[i].getUser());
        }
        int i1 = readInt() - 1;

        Book[] books = bookStorage.getAll();

        if (books.length == 0) {
            writeString("Books empty");
            return;
        }

        writeString("Choose book");
        for (int i = 0; i < books.length; i++) {
            writeString((i + 1) + " Book " + books[i].getTitle() + " " + books[i].getAuthor());
        }
        int i = readInt() - 1;
        Book book = books[i];

        orderService.addBook(all[i1].getId(), book);
        writeString("Successfully added");
    }

    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeObject(orderService.getById(id));
    }

    @Override
    public void getAll() {
        writeObjects(orderService.getAll());
    }

    @Override
    public void getAllByUser() {
        User[] users = userStorage.findAll();

        if (users.length == 0) {
            writeString("Users empty");
            return;
        }

        writeString("Choose user");
        for (int i1 = 0; i1 < users.length; i1++) {
            writeString((i1 + 1) + " User " + users[i1].getName() + " " + users[i1].getAddress());
        }
        int i1 = readInt() - 1;
        User user = users[i1];

        writeObjects(orderService.getAllByUser(user));
    }

    @Override
    public void getAllByAddress() {
        Address[] all = addressStorage.findAll();

        if (all.length == 0) {
            writeString("Addresses empty");
            return;
        }

        writeString("Choose address");
        for (int i = 0; i < all.length; i++) {
            writeString((i + 1) + " Address " + all[i].getStreet() + " " + all[i].getHome());
        }
        int i = readInt() - 1;
        Address address = all[i];

        writeObjects(orderService.getAllByAddress(address));
    }

    @Override
    public void getAllByType() {
        writeString("Choose type");
        Type[] values = Type.values();
        for (int i = 0; i < values.length; i++) {
            Type value = values[i];
            writeString((i + 1) + " Type " + value.name());
        }
        int i = readInt() - 1;

        writeObjects(orderService.getAllByType(values[i]));
    }

    @Override
    public void getAllByStatus() {
        writeString("Choose status");
        Status[] values = Status.values();
        for (int i = 0; i < values.length; i++) {
            Status value = values[i];
            writeString((i + 1) + " Status " + value.name());
        }
        int i = readInt() - 1;

        writeObjects(orderService.getAllByStatus(values[i]));
    }

    @Override
    public void getAllByStore() {
        Store[] stores = storeStorage.getAll();

        if (stores.length == 0) {
            writeString("Stores empty");
            return;
        }
        writeString("Choose store");
        for (int i = 0; i < stores.length; i++) {
            writeString((i + 1) + " User " + stores[i].getName() + " " + stores[i].getAddress());
        }
        int i = readInt() - 1;
        Store store = stores[i];

        writeObjects(orderService.getAllByStore(store));
    }
}
