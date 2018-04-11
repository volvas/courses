package com.devproserv.courses.form;

public class LoginValidation implements Validation {

    private final String login;
    private final String password;
    private String message;

    LoginValidation(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean validated() {
        if (login == null || password == null) {
            message = "Username and password should not be empty!";
            return false;
        }
        if (login.isEmpty() || password.isEmpty()) {
            message = "Username and password should not be empty!";
            return false;
        }
        if (login.matches("^(\\W|\\d)+.*")) {
            message = "Username should not start with a digit or non letter!";
            return false;
        }
        if (login.matches("\\w+\\W+.*")) {
            message = "Username should contain only letters and digits!";
            return false;
        }
        return true;
    }

    @Override
    public String errorMessage() {
        return message;
    }
}
