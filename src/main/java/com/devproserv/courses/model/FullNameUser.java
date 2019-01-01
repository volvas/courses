/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Vladimir
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

package com.devproserv.courses.model;

/**
 * User with first and last names.
 *
 * @since 0.5.0
 */
public final class FullNameUser {
    /**
     * User.
     */
    private final User user;

    /**
     * First name.
     */
    private final String fname;

    /**
     * Last name.
     */
    private final String lname;

    /**
     * Primary constructor.
     *
     * @param user User
     * @param fname First name
     * @param lname Last name
     */
    FullNameUser(final User user, final String fname, final String lname) {
        this.user  = user;
        this.fname = fname;
        this.lname = lname;
    }

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.user.getId();
    }

    /**
     * Getter.
     * @return Login
     */
    public String getLogin() {
        return this.user.getLogin();
    }

    /**
     * Getter.
     * @return Password
     */
    public String getPassword() {
        return this.user.getPassword();
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
}
