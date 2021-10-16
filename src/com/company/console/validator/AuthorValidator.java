package com.company.console.validator;

public class AuthorValidator extends AbstractValidator {
    public static boolean validName(String name) {
        return name.length() > 0;
    }

    public static boolean validDescription(String description) {
        return description.length() > 0;
    }
}
