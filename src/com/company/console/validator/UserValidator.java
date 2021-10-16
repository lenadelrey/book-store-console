package com.company.console.validator;

public class UserValidator extends AbstractValidator {
    public static boolean validName(String name) {
        return name.length() > 0;
    }

    public static boolean validLogin(String login) {
        return login.length() > 0;
    }

    public static boolean validPassword(String password){
        return password.length() > 0;
    }

    public static boolean validAge(int age) {
        return age > 0;
    }
}
