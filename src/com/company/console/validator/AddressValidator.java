package com.company.console.validator;

public class AddressValidator extends AbstractValidator{

    public static boolean validStreet(String street){
        return street.length() > 2;
    }

    public static boolean validHome(int home){
        return home > 0;
    }
}
