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

import com.devproserv.courses.form.EnrollForm;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the entity of the Administrator.
 *
 * @since 1.0.0
 */
public final class Admin implements Responsible {
    /**
     * User.
     */
    private final User user;

    /**
     * Primary constructor.
     *
     * @param user User instance
     */
    public Admin(final User user) {
        this.user = user;
    }

    @Override
    public Response response() {
        final String message = "This account is not accessible!";
        final Map<String, Object> payload = new HashMap<>();
        payload.put("message", message);
        return new Response(EnrollForm.LOGIN_PAGE, payload);
    }

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.user.getId();
    }
}
