package com.company.console.action;

import com.company.domain.Address;
import com.company.service.AddressService;
import com.company.service.AddressServiceImpl;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.*;
import static com.company.console.validator.AddressValidator.*;

public class ConsoleAddressAction implements AddressAction {
    private AddressService addressService = new AddressServiceImpl();

    @Override
    public void add() {
        writeString("Enter street");
        String street = readString();

        if (!validStreet(street)) {
            writeString("Invalid street");
            return;
        }

        writeString("Enter home");
        int home = readInt();

        if (!validHome(home)) {
            writeString("Invalid home");
            return;
        }

        addressService.add(new Address(street, home));
        writeString("Successfully added");
    }

    @Override
    public void updateStreetById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("invalid id");
            return;
        }

        writeString("Enter street");
        String street = readString();

        if (!validStreet(street)) {
            writeString("Invalid street");
            return;
        }

        addressService.updateStreetById(id, street);
        writeString("Successfully updated");
    }

    @Override
    public void updateHomeById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("invalid id");
            return;
        }

        writeString("Enter home");
        int home = readInt();

        if (!validHome(home)) {
            writeString("Invalid home");
            return;
        }

        addressService.updateHomeById(id, home);
        writeString("Successfully updated");
    }

    @Override
    public void deleteById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("invalid id");
            return;
        }

        addressService.deleteById(id);
        writeString("Successfully deleted");
    }

    @Override
    public void findAllByStreet() {
        writeString("Enter street");
        String street = readString();
        writeObjects(addressService.findAllByStreet(street));
    }

    @Override
    public void findAllByHome() {
        writeString("Enter home");
        int home = readInt();

        if (!validHome(home)) {
            writeString("Invalid home");
            return;
        }

        writeObjects(addressService.findAllByHome(home));
    }

    @Override
    public void findAll() {
        writeObjects(addressService.findAll());
    }

    @Override
    public void findById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeObject(addressService.findById(id));
    }
}
