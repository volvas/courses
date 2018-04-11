package com.devproserv.courses.form;

public class NumberValidation implements Validation {

    private final String number;
    private String message;

    public NumberValidation(String number) { // TODO make default access after refactoring subscribe
        this.number = number;
    }

    @Override
    public boolean validated() {
        boolean result = true;
        message = "ok";
        if (number == null || number.isEmpty()) {
            message = "Field should not be empty!";
            result = false;
        } else if (number.matches(".*\\D+.*")) {
            message = "Field should contain only digits";
            result = false;
        }
        return result;
    }

    @Override
    public String errorMessage() {
        return message;
    }
}
