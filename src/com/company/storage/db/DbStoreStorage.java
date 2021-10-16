package com.company.storage.db;

import com.company.domain.Address;
import com.company.domain.Store;
import com.company.storage.StoreStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbStoreStorage implements StoreStorage {

    Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Store store) {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into addresses values (default, ?, ?) returning id");
            preparedStatement1.setString(1, store.getAddress().getStreet());
            preparedStatement1.setInt(2, store.getAddress().getHome());
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            int storeId = resultSet.getInt(1);

            PreparedStatement preparedStatement = connection.prepareStatement("insert into stores values (default, ?, ?)");
            preparedStatement.setString(1, store.getName());
            preparedStatement.setInt(2, storeId);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Address updateAddressById(int id, Address address) {
        try {
            Store store = getById(id);
            Address old = store.getAddress();
            PreparedStatement preparedStatement = connection.prepareStatement("update stores set address_id = ? where id = ?");
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateName(int id, String name) {
        try {
            Store store = getById(id);
            String old = store.getName();
            PreparedStatement preparedStatement = connection.prepareStatement("update stores set name = ? where id = ?");
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
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from stores where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Address getAddress(int id) {
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
    public Store getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString(2);
            int addressId = resultSet.getInt(3);

            return new Store(id, name, getAddress(addressId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores where name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            int addressId = resultSet.getInt(3);

            return new Store(id, name, getAddress(addressId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getByAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores where address_id = ?");
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);

            return new Store(id, name, address);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Store[] getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores");
            List<Store> stores = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int addressId = resultSet.getInt(3);

                stores.add(new Store(id, name, getAddress(addressId)));
            }
            return stores.toArray(new Store[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores where id = ?");
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores where address_id = ?");
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores where id = ?");
            preparedStatement.setInt(1, store.getId());
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stores where name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
