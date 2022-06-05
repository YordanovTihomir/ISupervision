package com.example.isupervision.validator;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class InputValidator implements Predicate<String> {
    private final static Predicate<String> ID_VALID_EMAIL =
            Pattern.compile(
                    "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE).asPredicate();

    @Override
    public boolean test(String email) {
        return ID_VALID_EMAIL.test(email);
    }

}
