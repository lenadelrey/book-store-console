package com.company.storage.db;

import com.company.domain.*;
import com.company.storage.OrderStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbOrderStorage implements OrderStorage {

    Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Order order) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into orders values (default, ?, ?, ?, ?, ?, ?, ?)");
            if(order.getAddress() != null) {
                preparedStatement.setInt(1, order.getAddress().getId());
                preparedStatement.setInt(2, 0);
            } else{
                preparedStatement.setInt(1, 0);
                preparedStatement.setInt(2, order.getStore().getId());
            }
            preparedStatement.setInt(3, order.getUser().getId());
            preparedStatement.setBoolean(4, order.isDelivery());
            preparedStatement.setString(5, order.getType().name());
            preparedStatement.setString(6, order.getStatus().name());
            preparedStatement.setString(7, order.getApplication().name());

            Book[] books = order.getBooks();
            for (Book book : books) {
                PreparedStatement preparedStatement1 = connection.prepareStatement("insert into basket values(?, ?)");
                preparedStatement1.setInt(1, order.getId());
                preparedStatement1.setInt(2, book.getId());
                preparedStatement1.execute();
            }
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addApplication(Order order, Application application) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update orders set application = ? where id = ?");
            preparedStatement.setString(1, application.name());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void applicationProcess() {
        try {
            connection.prepareStatement("delete from orders where application IS NOT NULL");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Address updateAddressById(int id, Address address) {
        try {
            Order order = getById(id);
            Address old = order.getAddress();

            PreparedStatement preparedStatement = connection.prepareStatement("update orders set address_id = ? where id = ?");
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
    public Type updateTypeById(int id, Type type, Address address, Store store, boolean isDelivery) {
        try {
            Order order = getById(id);
            Type old = order.getType();

            PreparedStatement preparedStatement;

            if (order.getAddress() != null) {
                preparedStatement = connection.prepareStatement("update orders set type = ?, store_id = ?, 'isDelivery' = ? where id = ?");
                preparedStatement.setString(1, type.name());
                preparedStatement.setInt(2, store.getId());
                preparedStatement.setBoolean(3, isDelivery);
                preparedStatement.setInt(4, id);
                preparedStatement.execute();
            } else {
                preparedStatement = connection.prepareStatement("update orders set type = ?, address_id = ?, 'isDelivery' = ? where id = ?");
                preparedStatement.setString(1, type.name());
                preparedStatement.setInt(2, address.getId());
                preparedStatement.setBoolean(3, isDelivery);
                preparedStatement.setInt(4, id);
                preparedStatement.execute();
            }

            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Status updateStatus(int id, Status status) {
        try {
            Order order = getById(id);
            Status old = order.getStatus();

            PreparedStatement preparedStatement = connection.prepareStatement("update orders set status = ? where id = ?");
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order deleteById(int id) {
        try {
            Order old = getById(id);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from orders where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            PreparedStatement preparedStatement1 = connection.prepareStatement("delete from basket where order_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order delete(Order order) {
        try {
            Order old = getById(order.getId());
            PreparedStatement preparedStatement = connection.prepareStatement("delete from orders where id = ?");
            preparedStatement.setInt(1, order.getId());
            preparedStatement.execute();

            PreparedStatement preparedStatement1 = connection.prepareStatement("delete from basket where order_id = ?");
            preparedStatement1.setInt(1, order.getId());
            preparedStatement1.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Book deleteBook(int id, Book book) {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from basket where order_id = ? and book_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.setInt(2, book.getId());
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            String title = resultSet.getString(2);
            String description = resultSet.getString(3);
            int authorId = resultSet.getInt(4);
            int age = resultSet.getInt(5);
            Book old = new Book(id, title, description, getAuthor(authorId), age);

            PreparedStatement preparedStatement = connection.prepareStatement("delete from basket where order_id = ? and book_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, book.getId());
            preparedStatement.execute();
            return old;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addBook(int id, Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into basket values (?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, book.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
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

    private Store getStore(int id) {
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

    private User getUser(int id) {
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

            return new User(id, name, login, password, age, getAddress(addressId), getRole(roleName));
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

    private Status getStatus(String statusName) {
        Status status;
        switch (statusName) {
            case "ACTIVE": {
                status = Status.ACTIVE;
                return status;
            }
            case "CLOSE": {
                status = Status.CLOSE;
                return status;
            }
            default:
                throw new IllegalStateException("Wrong status: " + statusName);
        }
    }

    private Type getType(String typeName) {
        Type type;
        switch (typeName) {
            case "DELIVERY": {
                type = Type.DELIVERY;
                return type;
            }
            case "PICKUP": {
                type = Type.PICKUP;
                return type;
            }
            default:
                throw new IllegalStateException("Wrong type: " + typeName);
        }
    }

    private Author getAuthor(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from authors where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String authorName = resultSet.getString(2);
            String authorDescription = resultSet.getString(3);
            return new Author(id, authorName, authorDescription);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private List<Book> getBooksByOrderId(int id) {
        List<Book> bookList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select book_id from basket where order_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idBook = resultSet.getInt(1);
                PreparedStatement preparedStatement2 = connection.prepareStatement("select * from books where id = ?");
                preparedStatement2.setInt(1, idBook);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                resultSet2.next();
                String title = resultSet2.getString(2);
                String description = resultSet2.getString(3);
                int authorId = resultSet2.getInt(4);
                int price = resultSet2.getInt(5);

                bookList.add(new Book(idBook, title, description, getAuthor(authorId), price));
            }
            return bookList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int addressId = resultSet.getInt(2);
            int storeId = resultSet.getInt(3);
            int userId = resultSet.getInt(4);

            if (getAddress(addressId) != null) {
                return new Order(id, getAddress(addressId), getUser(userId), getBooksByOrderId(id).toArray(new Book[0]));
            } else {
                return new Order(id, getStore(storeId), getUser(userId), getBooksByOrderId(id).toArray(new Book[0]));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAll() {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int addressId = resultSet.getInt(2);
                int storeId = resultSet.getInt(3);
                int userId = resultSet.getInt(4);

                if (getAddress(addressId) != null) {
                    orders.add(new Order(orderId, getAddress(addressId), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                } else {
                    orders.add(new Order(orderId, getStore(storeId), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                }
            }
            return orders.toArray(new Order[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAllByUser(User user) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where user_id = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int addressId = resultSet.getInt(2);
                int storeId = resultSet.getInt(3);

                if (getAddress(addressId) != null) {
                    orders.add(new Order(orderId, getAddress(addressId), getUser(user.getId()), getBooksByOrderId(orderId).toArray(new Book[0])));
                } else {
                    orders.add(new Order(orderId, getStore(storeId), getUser(user.getId()), getBooksByOrderId(orderId).toArray(new Book[0])));
                }
            }
            return orders.toArray(new Order[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAllByAddress(Address address) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where address_id = ?");
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int storeId = resultSet.getInt(3);
                int userId = resultSet.getInt(4);

                if (getAddress(address.getId()) != null) {
                    orders.add(new Order(orderId, getAddress(address.getId()), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                } else {
                    orders.add(new Order(orderId, getStore(storeId), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                }
            }
            return orders.toArray(new Order[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAllByType(Type type) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where type = ?");
            preparedStatement.setString(1, type.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int addressId = resultSet.getInt(2);
                int storeId = resultSet.getInt(3);
                int userId = resultSet.getInt(4);

                if (getAddress(addressId) != null) {
                    orders.add(new Order(orderId, getAddress(addressId), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                } else {
                    orders.add(new Order(orderId, getStore(storeId), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                }
            }
            return orders.toArray(new Order[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where status = ?");
            preparedStatement.setString(1, status.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int addressId = resultSet.getInt(2);
                int storeId = resultSet.getInt(3);
                int userId = resultSet.getInt(4);

                if (getAddress(addressId) != null) {
                    orders.add(new Order(orderId, getAddress(addressId), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                } else {
                    orders.add(new Order(orderId, getStore(storeId), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                }
            }
            return orders.toArray(new Order[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAllByStore(Store store) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where store_id = ?");
            preparedStatement.setInt(1, store.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int addressId = resultSet.getInt(2);
                int userId = resultSet.getInt(4);

                if (getAddress(addressId) != null) {
                    orders.add(new Order(orderId, getAddress(addressId), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                } else {
                    orders.add(new Order(orderId, getStore(store.getId()), getUser(userId), getBooksByOrderId(orderId).toArray(new Book[0])));
                }
            }
            return orders.toArray(new Order[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean contains(Order order) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where id = ?");
            preparedStatement.setInt(1, order.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where user_id = ?");
            preparedStatement.setInt(1, user.getId());
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where address_id = ?");
            preparedStatement.setInt(1, address.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Type type) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where type = ?");
            preparedStatement.setString(1, type.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Status status) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where status = ?");
            preparedStatement.setString(1, status.name());
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where store_id = ?");
            preparedStatement.setInt(1, store.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
