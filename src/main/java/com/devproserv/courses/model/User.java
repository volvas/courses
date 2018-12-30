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

package com.devproserv.courses.model;

/**
 * Represent an entity of User from authenticating point of view.
 *
 * @since 1.0.0
 */
public abstract class User {
    /**
     * ID.
     */
    private int id;

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
     * Primary constructor.
     *
     * @param id ID
     * @param login Login
     * @param password Password
     * @param fname First name
     * @param lname Last name
     */
    public User(final int id, final String login, final String password,
        final String fname, final String lname
    ) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
    }

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter.
     * @param newid ID
     */
    public void setId(final int newid) {
        this.id = newid;
    }

    /**
     * Getter.
     * @return Login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Setter.
     * @param newlogin Login
     */
    public void setLogin(final String newlogin) {
        this.login = newlogin;
    }

    /**
     * Getter.
     * @return Password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter.
     * @param newpass Password
     */
    public void setPassword(final String newpass) {
        this.password = newpass;
    }

    /**
     * Getter.
     * @return First name
     */
    public String getFirstName() {
        return this.fname;
    }

    /**
     * Setter.
     * @param newfname First name
     */
    public void setFirstName(final String newfname) {
        this.fname = newfname;
    }

    /**
     * Getter.
     * @return Last name
     */
    public String getLastName() {
        return this.lname;
    }

    /**
     * Setter.
     * @param newlname Last name
     */
    public void setLastName(final String newlname) {
        this.lname = newlname;
    }

    /**
     * Returns payload.
     *
     * @return Response
     */
    public abstract Response response();
}
