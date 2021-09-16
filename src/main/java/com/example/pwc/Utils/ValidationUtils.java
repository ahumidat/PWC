package com.example.pwc.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmpty(String value){
        return value == null || value.isEmpty();
    }

    public static boolean isValidEmail(String email){
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.find();
    }

    public static boolean isValidRole(String role){
        return !isEmpty(role) && (role.equals(Role.Employee.name()) || role.equals(Role.Manager.name()));
    }
}
