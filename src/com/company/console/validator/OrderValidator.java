package com.company.console.validator;

import com.company.domain.Type;

public class OrderValidator extends AbstractValidator {
    public static boolean validType(Type type) {
        return type == null;
    }
}
