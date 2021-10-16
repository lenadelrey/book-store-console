package com.company.console;

import com.company.domain.Basket;
import com.company.domain.User;

public class Session {
    private User user;
    private Basket basket;

    public Session(User user, Basket basket) {
        this.user = user;
        this.basket = basket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Override
    public String toString() {
        return "Session{" +
                "user=" + user +
                ", basket=" + basket +
                '}';
    }
}
