package com.company.storage.file;

import com.company.domain.Address;
import com.company.domain.Role;
import com.company.domain.User;
import com.company.storage.UserStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUserStorage implements UserStorage {

    private static final int ID = 0;
    private static final String PATTERN = "%d %s %s %s %d %d %s";
    private static final int NAME = 1;
    private static final int LOGIN = 2;
    private static final int PASSWORD = 3;
    private static final int AGE = 4;
    private static final int ADDRESS_ID = 5;
    private static final int ROLE = 6;

    private void rewrite(List<User> users) {
        try {
            FileWriter fileWriter = new FileWriter("users.txt");
            String pattern = PATTERN;
            for (User user : users) {
                fileWriter.write(String.format(pattern, user.getId(), user.getName(), user.getLogin(), user.getPassword(), user.getAge(), user.getAddress().getId(), user.getRole().name()));
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
            if(Integer.parseInt(s[ID]) == id) {
                return new Address(Integer.parseInt(s[ID]), s[NAME], Integer.parseInt(s[2]));
            }
        }
        return null;
    }

    private List<User> getAll() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("users.txt"));
            List<User> users = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                int addressId = Integer.parseInt(s[ADDRESS_ID]);
                User user = new User(Integer.parseInt(s[ID]), s[NAME], s[LOGIN], s[PASSWORD], Integer.parseInt(s[AGE]), getAddressById(addressId),
                        Role.valueOf(s[ROLE]));
                users.add(user);
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            FileWriter fileWriter = new FileWriter("users.txt");

            String format = String.format(PATTERN, user.getId(), user.getName(), user.getLogin(), user.getPassword(), user.getAge(),
                    user.getAddress().getId(), user.getRole().name());

            fileWriter.write(format);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User updateNameById(int id, String name) {
        List<User> users = getAll();
        User old = null;
        for (User user : users) {
            if(user.getId() == id) {
                old = user;
                user.setName(name);
                break;
            }
        }
        rewrite(users);
        return old;
    }

    @Override
    public User updateLoginById(int id, String login) {
        List<User> users = getAll();
        User old = null;
        for (User user : users) {
            if(user.getId() == id) {
                old = user;
                user.setLogin(login);
                break;
            }
        }
        rewrite(users);
        return old;
    }

    @Override
    public User updatePasswordById(int id, String password) {
        List<User> users = getAll();
        User old = null;
        for (User user : users) {
            if(user.getId() == id) {
                old = user;
                user.setPassword(password);
                break;
            }
        }
        rewrite(users);
        return old;
    }

    @Override
    public User updateAgeById(int id, int age) {
        List<User> users = getAll();
        User old = null;
        for (User user : users) {
            if(user.getId() == id) {
                old = user;
                user.setAge(age);
                break;
            }
        }
        rewrite(users);
        return old;
    }

    @Override
    public User updateAddressById(int id, Address address) {
        List<User> users = getAll();
        User old = null;
        for (User user : users) {
            if(user.getId() == id) {
                old = user;
                user.setAddress(address);
                break;
            }
        }
        rewrite(users);
        return old;
    }

    @Override
    public User findById(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[ID]) == id) {
                    return new User(Integer.parseInt(s[ID]), s[NAME], s[LOGIN], s[PASSWORD], Integer.parseInt(s[AGE]), getAddressById(Integer.parseInt(s[ADDRESS_ID])), Role.valueOf(s[ROLE]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User[] findAll() {
        return getAll().toArray(new User[0]);
    }

    @Override
    public User[] findAllByName(String name) {
        List<User> all = getAll();
        List<User> users = new ArrayList<>();
        for (User user : all) {
            if (user.getName().equals(name)) {
                users.add(user);
            }
        }
        return users.toArray(new User[0]);
    }

    @Override
    public User findByLogin(String login) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[2].equals(login)) {
                    return new User(Integer.parseInt(s[ID]), s[NAME], s[LOGIN], s[PASSWORD], Integer.parseInt(s[AGE]), getAddressById(Integer.parseInt(s[ADDRESS_ID])), Role.valueOf(s[ROLE]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //filereader -> buffered reader -> list user -> process
        return null;
    }

    @Override
    public User[] findAllByAge(int age) {
        List<User> all = getAll();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getAge() == age) {
                users.add(all.get(i));
            }
        }
        return users.toArray(new User[0]);
    }

    @Override
    public User[] findAllByAddress(Address address) {
        List<User> all = getAll();
        List<User> users = new ArrayList<>();
        for (int i = ID; i < all.size(); i++) {
            if(all.get(i).getAddress().equals(address)) {
                users.add(all.get(i));
            }
        }
        return users.toArray(new User[0]);
    }

    @Override
    public boolean contains(User user) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[ID]) == user.getId()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(String login) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[LOGIN].equals(login)) {
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
            bufferedReader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[ID]) == id) {
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
            bufferedReader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[ADDRESS_ID]) == address.getId()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
