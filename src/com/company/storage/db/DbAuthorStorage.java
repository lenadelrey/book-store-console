package com.company.storage.db;

import com.company.domain.Author;
import com.company.storage.AuthorStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAuthorStorage implements AuthorStorage {

    Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into authors values (default, ?, ?)");
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getDescription());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateDescriptionById(int id, String description) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update authors set description = ? where id = ?");
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public String updateNameById(int id, String name) {
        try {
            Author author = findById(id);
            String old = author.getName();
            PreparedStatement preparedStatement = connection.prepareStatement("update authors set name = ? where id = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Author deleteById(int id) {
        try {
            Author old = findById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from authors where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Author deleteByName(String name) {
        try {
            Author old = findByName(name);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from authors where name = ?");
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from authors where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from authors where name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from authors where id = ? and name = ?");
            preparedStatement.setInt(1, author.getId());
            preparedStatement.setString(2, author.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Author[] findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from authors");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                String string = resultSet.getString(2);
                String string1 = resultSet.getString(3);
                authors.add(new Author(anInt, string, string1));
            }
            return authors.toArray(new Author[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Author findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from authors where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            return new Author(id, name, description);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Author findByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from authors where name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String description = resultSet.getString(3);
            return new Author(id, name, description);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
