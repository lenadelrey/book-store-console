package com.company.storage.db;

import com.company.domain.Address;
import com.company.domain.Role;
import com.company.domain.User;
import com.company.storage.UserStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUserStorage implements UserStorage {

    Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void add(User user) {
        try {

            PreparedStatement preparedStatement2 = connection.prepareStatement("insert into addresses values (default, ?, ?) returning id");
            preparedStatement2.setString(1, user.getAddress().getStreet());
            preparedStatement2.setInt(2, user.getAddress().getHome());
            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            int addressId = resultSet.getInt(1);

            PreparedStatement preparedStatement = connection.prepareStatement("insert into users values (default, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setInt(5, addressId);
            preparedStatement.setString(6, user.getRole().name());
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public User updateNameById(int id, String name) {
        try {
            User old = findById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("update users set name = ? where id = ?");
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
    public User updateLoginById(int id, String login) {
        try {
            User old = findById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("update users set login = ? where id = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User updatePasswordById(int id, String password) {
        try {
            User old = findById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("update users set password = ? where id = ?");
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateAgeById(int id, int age) {
        try {
            User old = findById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("update users set age = ? where id = ?");
            preparedStatement.setInt(1, age);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateAddressById(int id, Address address) {
        try {
            User old = findById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("update users set address_id = ? where id = ?");
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Address getAddressById(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from addresses where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String street = resultSet.getString(2);
            int home = resultSet.getInt(3);

            return new Address(id, street, home);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Role getRole(String roleName) {
        Role role;
        switch (roleName) {
            case "ADMIN": {
                role = Role.ADMIN;
                return role;
            }
            case "USER": {
                role = Role.USER;
                return role;
            }
            default:
                throw new IllegalStateException("Wrong role in db: " + roleName);
        }
    }

    @Override
    public User findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString(2);
            String login = resultSet.getString(3);
            String password = resultSet.getString(4);
            int age = resultSet.getInt(5);
            int addressId = resultSet.getInt(6);
            String roleName = resultSet.getString(7);

            return new User(id, name, login, password, age, getAddressById(addressId), getRole(roleName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User[] findAll() {
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
            ResultSet resultSet = preparedStatement.executeQuery();

            int id, age, addressId;
            String name, login, password, roleName;

            while (resultSet.next()) {
                id = resultSet.getInt(1);
                name = resultSet.getString(2);
                login = resultSet.getString(3);
                password = resultSet.getString(4);
                age = resultSet.getInt(5);
                addressId = resultSet.getInt(6);
                roleName = resultSet.getString(7);

                userList.add(new User(id, name, login, password, age, getAddressById(addressId), getRole(roleName)));
            }
            return userList.toArray(new User[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User[] findAllByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String login = resultSet.getString(3);
                String password = resultSet.getString(4);
                int age = resultSet.getInt(5);
                int addressId = resultSet.getInt(6);
                String roleName = resultSet.getString(7);

                userList.add(new User(id, name, login, password, age, getAddressById(addressId), getRole(roleName)));
            }

            return userList.toArray(new User[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where login = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(4);
            int age = resultSet.getInt(5);
            int addressId = resultSet.getInt(6);
            String roleName = resultSet.getString(7);

            return new User(id, name, login, password, age, getAddressById(addressId), getRole(roleName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User[] findAllByAge(int age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where age = ?");
            preparedStatement.setInt(1, age);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String login = resultSet.getString(3);
                String password = resultSet.getString(4);
                int addressId = resultSet.getInt(6);
                String roleName = resultSet.getString(7);

                userList.add(new User(id, name, login, password, age, getAddressById(addressId), getRole(roleName)));
            }

            return userList.toArray(new User[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User[] findAllByAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where address_id = ?");
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String login = resultSet.getString(3);
                String password = resultSet.getString(4);
                int age = resultSet.getInt(5);
                String roleName = resultSet.getString(7);

                userList.add(new User(id, name, login, password, age, address, getRole(roleName)));
            }

            return userList.toArray(new User[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where login = ?");
            preparedStatement.setString(1, login);
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where address_id = ?");
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
