package com.company.storage.file;

import com.company.console.ConsoleApplication;
import com.company.domain.*;
import com.company.storage.OrderStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOrderStorage implements OrderStorage {

    public static void main(String[] args) {
        String pattern = "%s %s ";
        int count = 4;
        StringBuilder b = new StringBuilder(pattern);
        for (int i = 0; i < count; i++) {
            if (i != count - 1) {
                b.append("%d,");
            } else {
                b.append("%d ");
            }
        }
        b.append("ACTIVE");
        String s = b.toString();
        System.out.println(s);
    }

    private Author getAuthorById(int id) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("authors.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == id) {
                    return new Author(Integer.parseInt(s[0]), s[1], s[2]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book getBookById(int id) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("books.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == id) {
                    return new Book(Integer.parseInt(s[0]), s[1], s[2], getAuthorById(Integer.parseInt(s[3])), Integer.parseInt(s[4]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void rewrite(List<Order> orders) {
        try {
            FileWriter fileWriter = new FileWriter("orders.txt");
            String pattern = "%d %d %d %d %s ";
            StringBuilder stringBuilder = new StringBuilder(pattern);

            for (Order order : orders) {
                Book[] books = order.getBooks();
                for (int i = 0; i < books.length; i++) {
                    if(i != books.length - 1) {
                        stringBuilder.append("%d,");
                    } else {
                        stringBuilder.append("%d ");
                    }
                }
                stringBuilder.append("%s %s %s");
                List<Integer> booksIds = new ArrayList<>();
                for (Book book : books) {
                    booksIds.add(book.getId());
                }
                fileWriter.write(String.format(pattern, order.getId(), order.getAddress().getId(), order.getStore().getId(), order.getUser().getId(),
                        order.isDelivery(), Arrays.toString(booksIds.toArray(new Integer[0])), order.getType(), order.getStatus(), order.getApplication()));
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Address getAddressById(int id) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("addresses.txt"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] s = line.split(" ");
            if (Integer.parseInt(s[0]) == id) {
                return new Address(Integer.parseInt(s[0]), s[1], Integer.parseInt(s[2]));
            }
        }
        return null;
    }

    private Store getStoreById(int id) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("stores.txt"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] s = line.split(" ");
            if (Integer.parseInt(s[0]) == id) {
                return new Store(Integer.parseInt(s[0]), s[1], getAddressById(Integer.parseInt(s[2])));
            }
        }
        return null;
    }

    private User getUserById(int id) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] s = line.split(" ");
            if (Integer.parseInt(s[0]) == id) {
                return new User(Integer.parseInt(s[0]), s[1], s[2], s[3], Integer.parseInt(s[4]), getAddressById(Integer.parseInt(s[5])), Role.valueOf(s[6]));
            }
        }
        return null;
    }



    private List<Order> findAll() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            List<Order> orders = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                int addressId = Integer.parseInt(s[1]);
                int storeId = Integer.parseInt(s[2]);
                int userId = Integer.parseInt(s[3]);
                String[] str = s[5].split(",");
                List<Book> books = new ArrayList<>();
                for (int i = 0; i < str.length; i++) {
                    books.add(getBookById(Integer.parseInt(str[i])));
                }
                Order order;
                if(addressId != 0){
                    order = new Order(Integer.parseInt(s[0]), getAddressById(addressId), getUserById(userId), books.toArray(new Book[0]));
                } else {
                    order = new Order(Integer.parseInt(s[0]), getStoreById(storeId), getUserById(userId), books.toArray(new Book[0]));
                }
                orders.add(order);
            }
            return orders;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Order order) {
        String pattern = "%d %d %d %d %s ";
        StringBuilder stringBuilder = new StringBuilder(pattern);
        Book[] books = order.getBooks();
        for (int i = 0; i < books.length; i++) {
            if(i != books.length - 1) {
                stringBuilder.append("%d,");
            } else {
                stringBuilder.append("%d ");
            }
        }
        stringBuilder.append("%s %s %s");
        List<Integer> booksIds = new ArrayList<>();
        for (Book book : books) {
            booksIds.add(book.getId());
        }

        String format = String.format(pattern, order.getId(), order.getAddress().getId(), order.getStore().getId(), order.getUser().getId(),
                order.isDelivery(), Arrays.toString(booksIds.toArray(new Integer[0])), order.getType().name(), order.getStatus().name(), order.getApplication().name());

        try {
            FileWriter fileWriter = new FileWriter("orders.txt");

            fileWriter.write(format);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addApplication(Order order, Application application) {
        order.setApplication(application);
    }

    @Override
    public void applicationProcess() {
        List<Order> orders = findAll();
        for (Order order : orders) {
            if(order.getApplication() != null) {
                orders.remove(order);
            }
        }
    }

    @Override
    public Address updateAddressById(int id, Address address) {
        List<Order> orders = findAll();
        Address old = null;
        for (Order order : orders) {
            if (order.getId() == id) {
                old = order.getAddress();
                order.setAddress(address);
                break;
            }
        }
        rewrite(orders);
        return old;
    }

    @Override
    public Type updateTypeById(int id, Type type, Address address, Store store, boolean isDelivery) {
        List<Order> orders = findAll();
        for (Order order : orders) {
            if(order.getId() == id) {
                order.setType(type);
                if(order.getType().equals(Type.DELIVERY) && isDelivery) {
                    order.setAddress(address);
                    order.setStore(new Store(0));
                    order.setDelivery(true);
                } else if(order.getType().equals(Type.PICKUP)) {
                    order.setStore(store);
                    order.setAddress(new Address(0, "", 0));
                    order.setDelivery(false);
                }
                break;
            }
        }
        rewrite(orders);
        return null;
    }

    @Override
    public Status updateStatus(int id, Status status) {
        List<Order> orders = findAll();
        Status old = null;
        for (Order order : orders) {
            if(order.getId() == id) {
                old = order.getStatus();
                order.setStatus(status);
            }
        }
        rewrite(orders);
        return old;
    }

    @Override
    public Order deleteById(int id) {
        List<Order> orders = findAll();
        Order old = null;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == id) {
                old = orders.get(i);
                orders.remove(orders.get(i));
                break;
            }
        }
        rewrite(orders);
        return old;
    }

    @Override
    public Order delete(Order order) {
        List<Order> orders = findAll();
        Order old = null;
        for (Order order1 : orders) {
            if (order1.equals(order)) {
                old = order1;
                orders.remove(order1);
                break;
            }
        }
        rewrite(orders);
        return old;
    }

    @Override
    public Book deleteBook(int id, Book book) {
        List<Order> all = findAll();
        for (Order order : all) {
            if (order.getId() == id) {
                Book[] books = order.getBooks();
                List<Book> books1 = Arrays.asList(books);
                for (int i = 0; i < books1.size(); i++) {
                    if (books1.get(i).getId() == book.getId()) {
                        books1.remove(i);
                        break;
                    }
                }
                order.setBooks(books1.toArray(new Book[0]));
                break;
            }
        }
        rewrite(all);
        return book;
    }

    @Override
    public boolean addBook(int id, Book book) {
        List<Order> all = findAll();
        for (Order order : all) {
            if (order.getId() == id) {
                Book[] books = order.getBooks();
                List<Book> books1 = Arrays.asList(books);
                books1.add(book);
                order.setBooks(books1.toArray(new Book[0]));
                break;
            }
        }
        rewrite(all);
        return false;
    }

    @Override
    public Order getById(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                int addressId = Integer.parseInt(s[1]);
                int storeId = Integer.parseInt(s[2]);
                int userId = Integer.parseInt(s[3]);
                if(Integer.parseInt(s[0]) == id) {
                    String[] str = s[5].split("[,]");
                    List<Book> books = new ArrayList<>();
                    for (int i = 0; i < str.length; i++) {
                        books.add(getBookById(Integer.parseInt(str[i])));
                    }
                    if(addressId != 0){
                        return new Order(Integer.parseInt(s[0]), getAddressById(addressId), getUserById(userId), books.toArray(new Book[0]));
                    } else {
                        return new Order(Integer.parseInt(s[0]), getStoreById(storeId), getUserById(userId), books.toArray(new Book[0]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] getAll() {
        return findAll().toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByUser(User user) {
        List<Order> all = findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getUser().equals(user)) {
                orders.add(all.get(i));
            }
        }
        return orders.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByAddress(Address address) {
        List<Order> all = findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getAddress().equals(address)) {
                orders.add(all.get(i));
            }
        }
        return orders.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByType(Type type) {
        List<Order> all = findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getType().equals(type)) {
                orders.add(all.get(i));
            }
        }
        return orders.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        List<Order> all = findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getStatus().equals(status)) {
                orders.add(all.get(i));
            }
        }
        return orders.toArray(new Order[0]);
    }

    @Override
    public Order[] getAllByStore(Store store) {
        List<Order> all = findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getStore().equals(store)) {
                orders.add(all.get(i));
            }
        }
        return orders.toArray(new Order[0]);
    }

    @Override
    public boolean contains(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if (Integer.parseInt(s[0]) == id) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Order order) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if (Integer.parseInt(s[0]) == order.getId()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if (Integer.parseInt(s[3]) == user.getId()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if (Integer.parseInt(s[1]) == address.getId()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Type type) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if (Type.valueOf(s[6]).equals(type)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Status status) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if (Status.valueOf(s[7]).equals(status)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("orders.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if (Integer.parseInt(s[2]) == store.getId()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
