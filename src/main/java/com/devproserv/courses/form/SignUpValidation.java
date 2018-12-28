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

import com.devproserv.courses.validation.VldFieldNotNullEmpty;
import com.devproserv.courses.validation.VldPassword;
import com.devproserv.courses.validation.VldUsername;
import com.devproserv.courses.validation.results.VldResult;
import com.devproserv.courses.validation.results.VldResultAggr;

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
     * @param params Parameters
     */
    public SignUpValidation(final SignupParams params) {
        this.login    = params.getLogin();
        this.password = params.getPassword();
        this.fname    = params.getFirstName();
        this.lname    = params.getLastName();
        this.faculty  = params.getFaculty();
    }

    @Override
    public VldResult validate() {
        return new VldResultAggr()
            .join(new VldUsername(this.login).validate())
            .join(new VldPassword(this.password).validate())
            .join(new VldFieldNotNullEmpty(this.fname, "First name").validate())
            .join(new VldFieldNotNullEmpty(this.lname, "Last name").validate())
            .join(new VldFieldNotNullEmpty(this.faculty, "Faculty").validate())
            .aggregate();
    }
}
