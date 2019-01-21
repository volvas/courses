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

package com.devproserv.courses.model.users;

import com.devproserv.courses.model.Response;
import com.devproserv.courses.model.ResponseMessage;
import com.devproserv.courses.model.Responsible;
import java.io.Serializable;

/**
 * Represent an entity of User from authenticating point of view.
 *
 * @since 0.5.0
 */
public final class User implements Responsible, Serializable {
    /**
     * Serial number.
     */
    private static final long serialVersionUID = -1937660789178008660L;

    /**
     * ID.
     */
    private final int id;

    /**
     * Login.
     */
    private final String login;

    /**
     * Password.
     */
    private final String password;

    /**
     * Primary constructor.
     *
     * @param id ID
     * @param login Login
     * @param password Password
     */
    public User(final int id, final String login, final String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @Override
    public Response response() {
        return new ResponseMessage("This account is not accessible!").response();
    }

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter.
     * @return Login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Getter.
     * @return Password
     */
    public String getPassword() {
        return this.password;
    }
}
