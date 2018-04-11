package com.devproserv.courses.form;

public class SignUpValidation implements Validation {

    private final String login;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String faculty;
    private String message;


    public SignUpValidation(String login, String password, String firstName, String lastName, String faculty) { // TODO make default access after refactoring sign up
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.faculty = faculty;
    }

    @Override
    public boolean validated() {
        boolean result = true;
        message = "ok";
        if (login == null || password == null) {
            message = "Username and password should not be empty!";
            result = false;
        } else if (login.isEmpty() || password.isEmpty()) {
            message = "Username and password should not be empty!";
            result = false;
        } else if (login.matches("^[^a-zA-Z]+.*")) {
            message = "Username should not start with a digit or non letter!";
            result = false;
        } else if (login.matches(".*\\W+.*")) {
            message = "Username should contain only letters and digits!";
            result = false;
        } else if (firstName == null || firstName.isEmpty()) {
            message = "First name should not be empty!";
            result = false;
        } else if (lastName == null || lastName.isEmpty()) {
            message = "Last name should not be empty!";
            result = false;
        } else if (faculty == null || faculty.isEmpty()) {
            message = "Faculty should not be empty!";
            result = false;
        }
        return result;
    }

    @Override
    public String errorMessage() {
        return message;
    }
}
