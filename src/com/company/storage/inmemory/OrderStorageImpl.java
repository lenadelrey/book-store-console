package com.company.storage.inmemory;

import com.company.domain.*;
import com.company.storage.OrderStorage;

import java.util.Arrays;

public class OrderStorageImpl implements OrderStorage {
    private static Order[] array = new Order[10];
    private static int incId = 1;
    private static int size = 0;

    @Override
    public void save(Order order) {
        array[size++] = order;
        order.setId(incId++);
    }

    @Override
    public void addApplication(Order order, Application application) {
        order.setApplication(application);
    }

    @Override
    public void applicationProcess() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getApplication() != null) {
                for (int j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
            }
        }
    }

    @Override
    public Address updateAddressById(int id, Address address) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            Address old = array[i].getAddress();
            if (array[i].getId() == id) {
                array[i].setAddress(address);
                return old;
            }
        }
        return null;
    }

    @Override
    public Type updateTypeById(int id, Type type, Address address, Store store, boolean isDelivery) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i].getId() == id) {
                array[i].setType(type);
                if (type.equals(Type.DELIVERY) && isDelivery) {
                    array[i].setAddress(address);
                    array[i].setDelivery(true);
                } else if (type.equals(Type.PICKUP)) {
                    array[i].setStore(store);
                    array[i].setDelivery(false);
                }
            }
        }
        return null;
    }

    @Override
    public Status updateStatus(int id, Status status) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            Status old = array[i].getStatus();
            if (array[i].getId() == id) {
                array[i].setStatus(status);
                return old;
            }
        }
        return null;
    }

    @Override
    public Order deleteById(int id) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            Order old = array[i];
            if(array[i].getId() == id) {
                for (int j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
                return old;
            }
        }
        return null;
    }

    @Override
    public Order delete(Order order) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            Order old = array[i];
            if(array[i] == order) {
                for (int j = i; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
                return old;
            }
        }
        return null;
    }

    @Override
    public Book deleteBook(int id, Book book) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getId() == id) {
                Book[] books = array[i].getBooks();
                for (int i1 = 0; i1 < books.length; i1++) {
                    Book old = books[i];
                    if (books[i1].equals(book)){
                        for (int j = i1; j < books.length - 1; j++) {
                            books[j] = books[j + 1];
                        }
                        return old;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean addBook(int id, Book book) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getId() == id) {
                Book[] books = array[i].getBooks();
                for (int i1 = 0; i1 < books.length; i1++) {
                    if(books[i] == null) {
                        books[i] = book;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Order getById(int id) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if (array[i].getId() == id) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public Order[] getAll() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public Order[] getAllByUser(User user) {
        int sizeUser = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getUser().equals(user)) {
                sizeUser++;
            }
        }
        Order[] orders = new Order[sizeUser];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getUser().equals(user)) {
                orders[j++] = array[i];
            }
        }
        return orders;
    }

    @Override
    public Order[] getAllByAddress(Address address) {
        int sizeUser = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getAddress().equals(address)) {
                sizeUser++;
            }
        }
        Order[] orders = new Order[sizeUser];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getAddress().equals(address)) {
                orders[j++] = array[i];
            }
        }
        return orders;
    }

    @Override
    public Order[] getAllByType(Type type) {
        int sizeUser = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getType().equals(type)) {
                sizeUser++;
            }
        }
        Order[] orders = new Order[sizeUser];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getType().equals(type)) {
                orders[j++] = array[i];
            }
        }
        return orders;
    }

    @Override
    public Order[] getAllByStatus(Status status) {
        int sizeUser = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getStatus().equals(status)) {
                sizeUser++;
            }
        }
        Order[] orders = new Order[sizeUser];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getStatus().equals(status)) {
                orders[j++] = array[i];
            }
        }
        return orders;
    }

    @Override
    public Order[] getAllByStore(Store store) {
        int sizeUser = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getStore().equals(store)) {
                sizeUser++;
            }
        }
        Order[] orders = new Order[sizeUser];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getStore().equals(store)) {
                orders[j++] = array[i];
            }
        }
        return orders;
    }

    @Override
    public boolean contains(int id) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Order order) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i] == order) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(User user) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Address address) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Type type) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Status status) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getStatus().equals(status)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Store store) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) break;
            if(array[i].getStore().equals(store)) {
                return true;
            }
        }
        return false;
    }
}
