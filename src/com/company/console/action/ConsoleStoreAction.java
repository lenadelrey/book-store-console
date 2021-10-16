package com.company.console.action;

import com.company.domain.Address;
import com.company.domain.Store;
import com.company.service.StoreService;
import com.company.service.StoreServiceImpl;
import com.company.storage.AddressStorage;
import com.company.storage.db.DbAddressStorage;
import com.company.storage.inmemory.AddressStorageImpl;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.*;
import static com.company.console.validator.AbstractValidator.validId;
import static com.company.console.validator.StoreValidator.validName;

public class ConsoleStoreAction implements StoreAction {
    private StoreService storeService = new StoreServiceImpl();
    private AddressStorage addressStorage = new DbAddressStorage();

    @Override
    public void add() {
        Store store = new Store();
        writeString("Enter name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }
        store.setName(name);

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
        store.setAddress(address);
        storeService.add(store);
        writeString("Successfully added");
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

        storeService.updateAddressById(id, address);
        writeString("Successfully updated");
    }

    @Override
    public void updateName() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Enter name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }

        storeService.updateName(id, name);
        writeString("Successfully updated");
    }

    @Override
    public void delete() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        storeService.delete(id);
        writeString("Successfully deleted");
    }

    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }
        writeObject(storeService.getById(id));
    }

    @Override
    public void getByName() {
        writeString("Enter name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }

        writeObject(storeService.getByName(name));
    }

    @Override
    public void getByAddress() {
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

        writeObject(storeService.getByAddress(address));
    }

    @Override
    public void getAll() {
        writeObjects(storeService.getAll());
    }
}
