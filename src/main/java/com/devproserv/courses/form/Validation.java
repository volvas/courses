package com.devproserv.courses.form;

/**
 * {@code Validation} class checks if data user inputs are valid
 * 
 * @author vovas11
 */
public interface Validation {

    boolean validated();

    String errorMessage();

    /**
     * Checks if login and password correspond to valid values (not null, not empty,
     * name should contain only letters and digits and do not start with a digit
     * 
     * @param login
     * @param password
     * @return string containing "ok" if credentials are valid or fault type
     */
    public static String checkCredentials(String login, String password) {

        String result = "ok";

        if (login == null || password == null) {
            return "Username and password should not be empty!";
        }
        if (login.isEmpty() || password.isEmpty()) {
            return "Username and password should not be empty!";
        }
        if (login.matches("^(\\W|\\d)+.+")) {
            return "Username should not start with a digit or non letter!";
        }
        if (login.matches("\\w+\\W+.*")) {
            return "Username should contain only letters and digits!";
        }
        return result;
    }

    /**
     * Overloaded method that checks fields during signing up.
     * Login and and password are validated as above.
     * Another fields should be not empty
     * 
     * @param login
     * @param password
     * @return string containing "ok" if credentials are valid or fault type
     */
    public static String checkCredentials(String login, String password,
            String firstName, String lastName, String faculty) {

        String result = "ok";
        // checks field of login and password
        result = checkCredentials(login, password);
        // checks remaining fields
        if (firstName == null || firstName.isEmpty()) {
            return "First name should not be empty!";
        }
        if (lastName == null || lastName.isEmpty()) {
            return "Last name should not be empty!";
        }
        if (faculty == null || faculty.isEmpty()) {
            return "Faculty should not be empty!";
        }
        return result;
    }

    /**
     * Checks given argument about it is integer or not
     * 
     * @param number
     * @return string containing "ok" if the number is valid Integer
     */
    public static String checkInteger(String number) {
        String result = "ok";
        // TODO make with regexp
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            result = "Invalid course ID!";
        }
        return result;
    }


}
