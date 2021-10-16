package com.company.console.action;

import com.company.domain.Author;
import com.company.service.AuthorService;
import com.company.service.AuthorServiceImpl;

import static com.company.console.util.ConsoleReader.readInt;
import static com.company.console.util.ConsoleReader.readString;
import static com.company.console.util.ConsoleWriter.*;
import static com.company.console.validator.AbstractValidator.validId;
import static com.company.console.validator.AuthorValidator.validDescription;
import static com.company.console.validator.AuthorValidator.validName;

public class ConsoleAuthorAction implements AuthorAction {
    private AuthorService authorService = new AuthorServiceImpl();

    @Override
    public void add() {
        Author author = new Author();

        writeString("Enter name");
        String name = readString();

        if (validName(name)) {
            author.setName(name);
        } else {
            writeString("Invalid name");
            return;
        }

        writeString("Enter description");
        String description = readString();

        if (!validDescription(description)) {
            writeString("Invalid description");
            return;
        }
        author.setDescription(description);
        authorService.add(author);
        writeString("Successfully added");
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

        authorService.updateDescriptionById(id, description);
        writeString("Successfully updated");
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

        authorService.updateNameById(id, name);
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

        authorService.deleteById(id);
        writeString("Successfully deleted");
    }

    @Override
    public void deleteByName() {
        writeString("Enter name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }

        authorService.deleteByName(name);
        writeString("Successfully deleted");
    }

    @Override
    public void findAll() {
        writeObjects(authorService.findAll());
    }

    @Override
    public void findById() {
        writeString("Enter id");
        int id = readInt();

        if (!validId(id)) {
            writeString("Invalid id");
            return;
        }

        writeObject(authorService.findById(id));
    }

    @Override
    public void findByName() {
        writeString("Enter name");
        String name = readString();

        if (!validName(name)) {
            writeString("Invalid name");
            return;
        }

        writeObject(authorService.findByName(name));
    }
}
