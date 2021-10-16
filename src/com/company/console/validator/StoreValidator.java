package com.company.console.validator;

import com.company.domain.Address;

public class StoreValidator extends AbstractValidator {

    public static boolean validName(String name) {
        return name.length() > 0;
    }

    public static boolean validAddress(Address address) {
        return address != null;
    }
}
