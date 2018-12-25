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

import javax.servlet.http.HttpServletRequest;

/**
 * Represent an entity of User.
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
     * Path.
     */
    private String path;

    /**
     * Primary constructor.
     * @param login Login
     * @param password Password
     */
    public User(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Loads fields for the user.
     */
    public abstract void loadFields();

    /**
     * Loads data to request.
     * @param request HTTP request
     */
    public abstract void prepareJspData(HttpServletRequest request);

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter.
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
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
     * @param login Login
     */
    public void setLogin(final String login) {
        this.login = login;
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
     * @param password Password
     */
    public void setPassword(final String password) {
        this.password = password;
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
     * @param fname First name
     */
    public void setFirstName(final String fname) {
        this.fname = fname;
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
     * @param lname Last name
     */
    public void setLastName(final String lname) {
        this.lname = lname;
    }

    /**
     * Getter.
     * @return Path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Setter.
     * @param path Path
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Returns payload.
     *
     * @return Response
     */
    public abstract Response response();
}
