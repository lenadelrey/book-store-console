package com.company.domain;

import java.util.Objects;

public class User {

    private int id;
    private String name;
    private String login;
    private String password;
    private int age;
    private Address address;
    private Role role;

    public User() {
    }

    public User(int id, String name, String login, String password, int age, Address address, Role role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
        this.address = address;
        this.role = role;
    }

    public User(String name, String login, String password, int age, Role role) {
        this.role = role;
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public User(int id, String name, String login, String password, int age, Role role) {
        this.role = role;
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public User(String name, String login, String password, int age, Address address, Role role) {
        this.role = role;
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
        this.address = address;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", role=" + role +
                '}';
    }
}
