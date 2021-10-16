package com.company.storage.file;

import com.company.domain.Address;
import com.company.storage.AddressStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAddressStorage implements AddressStorage {

    private void rewrite(List<Address> addresses) {
        try {
            FileWriter fileWriter = new FileWriter("addresses.txt");
            String pattern = "%d %s %d";
            for (Address address : addresses) {
                fileWriter.write(String.format(pattern, address.getId(), address.getStreet(), address.getHome()));
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Address> getAll() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("addresses.txt"));
            List<Address> addresses = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                Address address = new Address(Integer.parseInt(s[0]), s[1], Integer.parseInt(s[2]));
                addresses.add(address);
            }
            return addresses;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Address address) {
        try {
            FileWriter fileWriter = new FileWriter("addresses.txt");

            String pattern = "%d %s %d";
            String format = String.format(pattern, address.getId(), address.getStreet(), address.getHome());

            fileWriter.write(format);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String updateStreetById(int id, String street) {
        List<Address> addresses = getAll();
        String old = null;
        for (Address address : addresses) {
            if(address.getId() == id){
                old = address.getStreet();
                address.setStreet(street);
                break;
            }
        }
        rewrite(addresses);
        return old;
    }

    @Override
    public int updateHomeById(int id, int home) {
        List<Address> addresses = getAll();
        int old = 0;
        for (Address address : addresses) {
            if(address.getId() == id){
                old = address.getHome();
                address.setHome(home);
                break;
            }
        }
        rewrite(addresses);
        return old;
    }

    @Override
    public Address deleteById(int id) {
        List<Address> addresses = getAll();
        Address old = null;
        for (Address address : addresses) {
            if(address.getId() == id){
                old= address;
                addresses.remove(address);
                break;
            }
        }
        rewrite(addresses);
        return old;
    }

    @Override
    public Address[] findAllByStreet(String street) {
        List<Address> addresses = getAll();
        List<Address> streetAddresses = new ArrayList<>();
        for (int i = 0; i < addresses.size(); i++) {
            if(addresses.get(i).getStreet().equals(street)) {
                streetAddresses.add(addresses.get(i));
            }
        }
        return streetAddresses.toArray(new Address[0]);
    }

    @Override
    public Address[] findAllByHome(int home) {
        List<Address> addresses = getAll();
        List<Address> homeAddresses = new ArrayList<>();
        for (int i = 0; i < addresses.size(); i++) {
            if(addresses.get(i).getHome() == home) {
                homeAddresses.add(addresses.get(i));
            }
        }
        return homeAddresses.toArray(new Address[0]);
    }

    @Override
    public Address[] findAll() {
        return getAll().toArray(new Address[0]);
    }

    @Override
    public Address findById(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("addresses.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == id) {
                    return new Address(Integer.parseInt(s[0]), s[1], Integer.parseInt(s[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(String street, int home) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("addresses.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[2]) == home && s[1].equals(street)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("addresses.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
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
    public boolean contains(String street) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("addresses.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[1].equals(street)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
