package com.company.console.validator;

public abstract class AbstractValidator {

    public static boolean validId(int id){
        return id > 0;
    }
}
