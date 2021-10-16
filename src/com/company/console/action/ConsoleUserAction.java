package com.company.console.action;

import com.company.console.ConsoleApplication;
import com.company.console.Session;
import com.company.domain.Address;
import com.company.domain.Basket;
import com.company.domain.Role;
import com.company.domain.User;
import com.company.service.UserService;
import com.company.service.UserServiceImpl;
import com.company.storage.AddressStorage;
import com.company.storage.db.DbAddressStorage;
import com.company.storage.inmemory.AddressStorageImpl;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.*;
import static com.company.console.validator.UserValidator.*;

public class ConsoleUserAction implements UserAction {

    private UserService userService = new UserServiceImpl();
    private AddressStorage addressStorage = new DbAddressStorage();

    @Override
    public void add() {
        writeString("Enter name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }

        writeString("Enter login");
        String login = readString();

        if (!validLogin(login)) {
            writeString("Invalid login");
            return;
        }

        writeString("Enter password");
        String password = readString();

        if (!validPassword(password)) {
            writeString("Invalid password");
            return;
        }

        writeString("Enter your age");
        int age = readInt();

        if (!validAge(age)) {
            writeString("Invalid age");
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

        userService.add(new User(name, login, password, age, address, Role.ADMIN));
        writeString("Successfully added");
    }

    @Override
    public void registration() {
        writeString("Enter name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }

        writeString("Enter login");
        String login = readString();

        if (!validLogin(login)) {
            writeString("Invalid login");
            return;
        }

        writeString("Enter password");
        String password = readString();

        if (!validPassword(password)) {
            writeString("Invalid password");
            return;
        }

        writeString("Enter your age");
        int age = readInt();

        if (!validAge(age)) {
            writeString("Invalid age");
            return;
        }

        writeString("Enter street");
        String street = readString();

        if(street.length() <= 0) {
            writeString("Invalid street");
            return;
        }

        writeString("enter number of the house");
        int home = readInt();

        if(home <= 0) {
            writeString("Invalid number");
            return;
        }

        userService.add(new User(name, login, password, age, new Address(street, home), Role.USER));
        writeString("Successfully added");
    }

    @Override
    public void authorization() {
        writeString("Enter login");
        String login = readString();

        User byLogin = userService.getByLogin(login);
        if (byLogin != null) {
            writeString("Enter password");
            String password = readString();
            if (byLogin.getPassword().equals(password)) {
                ConsoleApplication.session = new Session(byLogin, new Basket());
            } else {
                writeString("Wrong password");
            }
        } else writeString("User not found");
    }

    @Override
    public void logout() {
        ConsoleApplication.session = null;
    }

    @Override
    public void updateNameById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Enter new name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }

        userService.updateNameById(id, name);
        writeString("Successfully updated");
    }

    @Override
    public void updateNameForUser() {
        writeString("Enter name");
        String name = readString();

        if(!validName(name)) {
            writeString("Invalid name");
            return;
        }

        ConsoleApplication.session.getUser().setName(name);
        writeString("Successfully updated");
    }

    @Override
    public void updateLoginById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Enter new login");
        String login = readString();

        if (!validLogin(login)) {
            writeString("Invalid login");
            return;
        }

        userService.updateLoginById(id, login);
        writeString("Successfully updated");
    }

    @Override
    public void updatePasswordForUser() {
        writeString("Enter new password");
        String password = readString();

        if(!validPassword(password)) {
            writeString("Invalid password");
            return;
        }

        ConsoleApplication.session.getUser().setPassword(password);
        writeString("Successfully updated");
    }

    @Override
    public void updatePasswordById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Enter new password");
        String password = readString();

        if (!validPassword(password)) {
            writeString("Invalid password");
            return;
        }

        userService.updatePasswordById(id, password);
        writeString("Successfully updated");
    }

    @Override
    public void updateAgeById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeString("Enter new age");
        int age = readInt();

        if (!validAge(age)) {
            writeString("Invalid age");
            return;
        }

        userService.updateAgeById(id, age);
        writeString("Successfully updated");
    }

    @Override
    public void updateAgeForUser() {
        writeString("Enter age");
        int age = readInt();

        if(!validAge(age)) {
            writeString("Invalid age");
            return;
        }

        ConsoleApplication.session.getUser().setAge(age);
        writeString("Successfully updated");
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

        userService.updateAddressById(id, address);
        writeString("Successfully updated");
    }

    @Override
    public void getById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeObject(userService.getById(id));
    }

    @Override
    public void getAll() {
        writeObjects(userService.getAll());
    }

    @Override
    public void getAllByName() {
        writeString("Enter name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }

        writeObjects(userService.getAllByName(name));
    }

    @Override
    public void getByLogin() {
        writeString("Enter login");
        String login = readString();

        if (!validLogin(login)) {
            writeString("Invalid login");
            return;
        }

        writeObject(userService.getByLogin(login));
    }

    @Override
    public void getAllByAge() {
        writeString("Enter new age");
        int age = readInt();

        if (!validAge(age)) {
            writeString("Invalid age");
            return;
        }

        writeObjects(userService.getAllByAge(age));
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

        writeObjects(userService.getAllByAddress(address));
    }
}
