package com.example.testmodule3.utils;

import java.util.regex.Pattern;

public class ValidateUtil {
    public static final String FULLNAME_REGEX = "^[a-zA-Z \\-\\.\\']*$";
    public static boolean isNameValid(String name) {
        return Pattern.matches(FULLNAME_REGEX, name);
    }
}
