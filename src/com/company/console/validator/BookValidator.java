package com.company.console.validator;

public class BookValidator extends AbstractValidator{

    public static boolean validPrice(int price) {
        return price > 0;
    }

    public static boolean validTitle(String title) {
        return title.length() > 0;
    }

    public static boolean validDescription(String description) {
        return description.length() > 0;
    }
}
