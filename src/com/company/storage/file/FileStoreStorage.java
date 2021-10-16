package com.company.storage.file;

import com.company.domain.Address;
import com.company.domain.Store;
import com.company.storage.StoreStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStoreStorage implements StoreStorage {

    private void rewrite(List<Store> stores) {
        try {
            FileWriter fileWriter = new FileWriter("stores.txt");
            String pattern = "%d %s %d";
            for (Store store : stores) {
                fileWriter.write(String.format(pattern, store.getId(), store.getName(), store.getAddress().getId()));
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
            if(Integer.parseInt(s[0]) == id) {
                return new Address(Integer.parseInt(s[0]), s[1], Integer.parseInt(s[2]));
            }
        }
        return null;
    }

    private List<Store> findAll() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("stores.txt"));
            List<Store> stores = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                int addressId = Integer.parseInt(s[2]);
                Store store = new Store(Integer.parseInt(s[0]), s[1], getAddressById(addressId));
                stores.add(store);
            }
            return stores;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Store store) {
        try {
            FileWriter fileWriter = new FileWriter("stores.txt");

            String pattern = "%d %s %d";
            String format = String.format(pattern, store.getId(), store.getName(), store.getAddress().getId());

            fileWriter.write(format);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address updateAddressById(int id, Address address) {
        List<Store> stores = findAll();
        Address old = null;
        for (Store store : stores) {
            if(store.getId() == id) {
                old = store.getAddress();
                store.setAddress(address);
                break;
            }
        }
        rewrite(stores);
        return old;
    }

    @Override
    public String updateName(int id, String name) {
        List<Store> stores = findAll();
        String old = null;
        for (Store store : stores) {
            if(store.getId() == id) {
                old = store.getName();
                store.setName(name);
                break;
            }
        }
        rewrite(stores);
        return old;
    }

    @Override
    public void delete(int id) {
        List<Store> stores = findAll();
        for (Store store : stores) {
            if(store.getId() == id) {
                stores.remove(store);
                break;
            }
        }
        rewrite(stores);
    }

    @Override
    public Store getById(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("stores.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == id) {
                    return new Store(Integer.parseInt(s[0]), s[1], getAddressById(Integer.parseInt(s[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getByName(String name) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("stores.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[1].equals(name)) {
                    return new Store(Integer.parseInt(s[0]), s[1], getAddressById(Integer.parseInt(s[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store getByAddress(Address address) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("stores.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[2]) == address.getId()) {
                    return new Store(Integer.parseInt(s[0]), s[1], getAddressById(Integer.parseInt(s[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Store[] getAll() {
        return findAll().toArray(new Store[0]);
    }

    @Override
    public boolean contains(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("stores.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == id) {
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
            bufferedReader = new BufferedReader(new FileReader("stores.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[2]) == address.getId()) {
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
            bufferedReader = new BufferedReader(new FileReader("stores.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == store.getId()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String name) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("stores.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[1].equals(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
