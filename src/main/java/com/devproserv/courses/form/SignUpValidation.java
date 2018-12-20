/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Vladimir
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.devproserv.courses.form;

/**
 * Sign up validation.
 */
public class SignUpValidation implements Validation {

    private final String login;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String faculty;
    private String message;

    SignUpValidation(
            final String login, final String password, final String firstName,
            final String lastName, final String faculty
    ) {
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
