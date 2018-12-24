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
 *
 * @since 1.0.0
 */
public final class SignUpValidation implements Validation {
    /**
     * Login.
     */
    private final String login;

    /**
     * Password.
     */
    private final String password;

    /**
     * First name.
     */
    private final String fname;

    /**
     * Last name.
     */
    private final String lname;

    /**
     * Faculty.
     */
    private final String faculty;

    /**
     * Validation message.
     */
    private String message;

    /**
     * Constructor.
     * @param login Login
     * @param password Password
     * @param fname First name
     * @param lname Last name
     * @param faculty Faculty
     */
    SignUpValidation(
        final String login, final String password, final String fname,
        final String lname, final String faculty
    ) {
        this.login    = login;
        this.password = password;
        this.fname    = fname;
        this.lname    = lname;
        this.faculty  = faculty;
    }

    /**
     * Constructor.
     * @param pars Parameters
     */
    public SignUpValidation(final SignupPars pars) {
        this.login    = pars.getLogin();
        this.password = pars.getPassword();
        this.fname    = pars.getFirstName();
        this.lname    = pars.getLastName();
        this.faculty  = pars.getFaculty();
    }

    @Override
    public boolean validated() {
        boolean result = true;
        this.message = "ok";
        if (this.login == null || this.password == null) {
            this.message = "Username and password should not be empty!";
            result = false;
        } else if (this.login.isEmpty() || this.password.isEmpty()) {
            this.message = "Username and password should not be empty!";
            result = false;
        } else if (this.login.matches("^[^a-zA-Z]+.*")) {
            this.message = "Username shouldn't start with digit or non letter!";
            result = false;
        } else if (this.login.matches(".*\\W+.*")) {
            this.message = "Username should contain only letters and digits!";
            result = false;
        } else if (this.fname == null || this.fname.isEmpty()) {
            this.message = "First name should not be empty!";
            result = false;
        } else if (this.lname == null || this.lname.isEmpty()) {
            this.message = "Last name should not be empty!";
            result = false;
        } else if (this.faculty == null || this.faculty.isEmpty()) {
            this.message = "Faculty should not be empty!";
            result = false;
        }
        return result;
    }

    @Override
    public String errorMessage() {
        return this.message;
    }
}
