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

package com.devproserv.courses.validation.results;

import com.devproserv.courses.validation.VldFieldNotNullEmpty;
import com.devproserv.courses.validation.VldPassword;
import com.devproserv.courses.validation.VldUsername;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates several validation result in one object.
 *
 * @since 1.0.0
 */
public class VldResultAggr {
    /**
     * Stores intermediate results.
     */
    private final List<VldResult> results = new ArrayList<>(8);

    /**
     * Validates username.
     *
     * @param username Username
     * @return This instance
     */
    public VldResultAggr checkUsername(final String username) {
        this.results.add(new VldUsername(username).validate());
        return this;
    }

    /**
     * Validates password.
     *
     * @param password Password
     * @return This instance
     */
    public VldResultAggr checkPassword(final String password) {
        this.results.add(new VldPassword(password).validate());
        return this;
    }

    /**
     * Validates field to ensure non null and non empty content.
     *
     * @param field Field
     * @param name Name of the field
     * @return This instance
     */
    public VldResultAggr checkFieldNotNullEmpty(final String field, final String name) {
        this.results.add(new VldFieldNotNullEmpty(field, name).validate());
        return this;
    }

    /**
     * Returns final result. If someone is not valid, it'll be sent
     *  as a result. So first join most important results.
     *
     * @return Final validation result
     */
    public VldResult aggregate() {
        return this.results.stream()
            .filter(result -> !result.valid())
            .findFirst()
            .orElse(new VldResultValid());
    }
}
