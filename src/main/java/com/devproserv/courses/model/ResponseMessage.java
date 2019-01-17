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

import com.devproserv.courses.form.EnrollForm;
import java.util.HashMap;
import java.util.Map;

/**
 * Auxiliary class wrapping message to response.
 *
 * @since 0.5.4
 */
class ResponseMessage {
    /**
     * Message to user.
     */
    private final String msg;

    /**
     * Primary constructor.
     *
     * @param msg Message to user
     */
    ResponseMessage(final String msg) {
        this.msg = msg;
    }

    /**
     * Creates a response with the provided message.
     *
     * @return Response with message and empty payload
     */
    Response response() {
        final Map<String, Object> payload = new HashMap<>();
        payload.put("message", this.msg);
        return new Response(EnrollForm.LOGIN_PAGE, payload);
    }
}
