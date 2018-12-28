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

import javax.servlet.http.HttpServletRequest;

/**
 * Extracts sign up form parameters from HTTP request.
 *
 * @since 1.0.0
 */
public class SignupParams {
    /**
     * HTTP request.
     */
    private final HttpServletRequest request;

    /**
     * Login.
     */
    private String login;

    /**
     * Password.
     */
    private String password;

    /**
     * First name.
     */
    private String fname;

    /**
     * Last name.
     */
    private String lname;

    /**
     * Faculty.
     */
    private String faculty;

    /**
     * Constructor.
     * @param request HTTP request
     */
    public SignupParams(final HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Extracts parameters from the HTTP request.
     * @return This instance
     */
    public SignupParams extract() {
        this.login    = this.request.getParameter("login");
        this.password = this.request.getParameter("password");
        this.fname    = this.request.getParameter("firstname");
        this.lname    = this.request.getParameter("lastname");
        this.faculty  = this.request.getParameter("faculty");
        return this;
    }

    /**
     * Getter.
     * @return Login
     */
    String getLogin() {
        return this.login;
    }

    /**
     * Getter.
     * @return Password
     */
    String getPassword() {
        return this.password;
    }

    /**
     * Getter.
     * @return First name
     */
    String getFirstName() {
        return this.fname;
    }

    /**
     * Getter.
     * @return Last name
     */
    String getLastName() {
        return this.lname;
    }

    /**
     * Getter.
     * @return Faculty
     */
    String getFaculty() {
        return this.faculty;
    }
}
