package com.company.storage.db;

import com.company.domain.Address;
import com.company.storage.AddressStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAddressStorage implements AddressStorage {
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into addresses values(default, ?, ?)");
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setInt(2, address.getHome());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String updateStreetById(int id, String street) {
        try {
            Address address = findById(id);
            String old = address.getStreet();

            PreparedStatement preparedStatement = connection.prepareStatement("update addresses set street = ? where id = ?");
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateHomeById(int id, int home) {
        try {
            Address address = findById(id);
            int old = address.getHome();

            PreparedStatement preparedStatement = connection.prepareStatement("update addresses set home = ? where id = ?");
            preparedStatement.setInt(1, home);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public Address deleteById(int id) {
        try {
            Address old = findById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from addresses where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Address[] findAllByStreet(String street) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from addresses where street = ?");
            preparedStatement.setString(1, street);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Address> addresses = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int home = resultSet.getInt(3);
                addresses.add(new Address(id, street, home));
            }
            return addresses.toArray(new Address[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Address[] findAllByHome(int home) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from addresses where home = ?");
            preparedStatement.setInt(1, home);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Address> addresses = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String string = resultSet.getString(2);
                addresses.add(new Address(id, string, home));
            }
            preparedStatement.execute();
            return addresses.toArray(new Address[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Address[] findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from addresses");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Address> addresses = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String street = resultSet.getString(2);
                int home = resultSet.getInt(3);
                addresses.add(new Address(id, street, home));
            }
            preparedStatement.execute();
            return addresses.toArray(new Address[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Address findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from addresses where id = ?");
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

    @Override
    public boolean contains(String street, int home) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from addresses where street = ? and home = ?");
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, home);
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from addresses where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String street) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from addresses where street = ?");
            preparedStatement.setString(1, street);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
