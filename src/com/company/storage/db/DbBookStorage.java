package com.company.storage.db;

import com.company.domain.Author;
import com.company.domain.Book;
import com.company.storage.BookStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbBookStorage implements BookStorage {

    Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into books values (default, ?, ?, ?, ?)");
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setInt(3, book.getAuthor().getId());
            preparedStatement.setInt(4, book.getPrice());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String updateTitleById(int id, String newTitle) {
        try {
            Book book = getById(id);
            String old = book.getTitle();
            PreparedStatement preparedStatement = connection.prepareStatement("update books set title = ? where id = ?");
            preparedStatement.setString(1, newTitle);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateDescriptionById(int id, String newDescription) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update books set description = ? where id = ?");
            preparedStatement.setString(1, newDescription);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int updatePriceById(int id, int newPrice) {
        try {
            Book book = getById(id);
            int old = book.getPrice();
            PreparedStatement preparedStatement = connection.prepareStatement("update books set price = ? where id = ?");
            preparedStatement.setInt(1, newPrice);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public Author updateAuthor(int id, Author author) {
        try {
            Book book = getById(id);
            Author old = book.getAuthor();
            PreparedStatement preparedStatement = connection.prepareStatement("update books set author_id = ? where id = ?");
            preparedStatement.setInt(1, author.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Book deleteById(int id) {
        try {
            Book old = getById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from books where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Book deleteByTitle(String title) {
        try {
            Book old = getByTitle(title);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from books where title = ?");
            preparedStatement.setString(1, title);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAllByAuthor(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from books where author_id = ?");
            preparedStatement.setInt(1, author.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Author getAuthorById(int id) {
        PreparedStatement preparedStatement1 = null;
        try {
            preparedStatement1 = connection.prepareStatement("select * from authors where id = ?");
            preparedStatement1.setInt(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            String name = resultSet1.getString(2);
            String description = resultSet1.getString(3);
            return new Author(id, name, description);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String title = resultSet.getString(2);
            String description = resultSet.getString(3);
            int authorId = resultSet.getInt(4);
            int price = resultSet.getInt(5);

            return new Book(id, title, description, getAuthorById(authorId), price);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where title = ?");
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String description = resultSet.getString(3);
            int authorId = resultSet.getInt(4);
            int price = resultSet.getInt(5);
            return new Book(id, title, description, getAuthorById(authorId), price);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                int authorId = resultSet.getInt(4);
                int price = resultSet.getInt(5);

                bookList.add(new Book(id, title, description, getAuthorById(authorId), price));
            }
            return bookList.toArray(new Book[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] getAllByPrice(int price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where price = ?");
            preparedStatement.setInt(1, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                int authorId = resultSet.getInt(4);

                bookList.add(new Book(id, title, description, getAuthorById(authorId), price));
            }
            return bookList.toArray(new Book[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] getAllByAuthor(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where author_id = ?");
            preparedStatement.setInt(1, author.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                int price = resultSet.getInt(5);

                bookList.add(new Book(id, title, description, author, price));
            }
            return bookList.toArray(new Book[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where title = ?");
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where id = ?");
            preparedStatement.setInt(1, book. getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where id = ?");
            preparedStatement.setInt(1, id);
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where author_id = ?");
            preparedStatement.setInt(1, author.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
