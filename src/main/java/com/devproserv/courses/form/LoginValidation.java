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

import com.devproserv.courses.validation.VldResult;
import com.devproserv.courses.validation.VldRuleContainsLetters;
import com.devproserv.courses.validation.VldRuleNotEmpty;
import com.devproserv.courses.validation.VldRuleNotNull;
import com.devproserv.courses.validation.VldRuleStartLetter;

/**
 * Validation of the login form.
 *
 * @since 1.0.0
 */
public final class LoginValidation implements Validation {
    /**
     * Login.
     */
    private final String login;

    /**
     * Password.
     */
    private final String password;

    /**
     * Constructor.
     * @param login Login
     * @param password Password
     */
    public LoginValidation(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public VldResult validate() {
        final String notnull = "Username and password should not be null!";
        final String notempty = "Username and password should not be empty!";
        final VldResult resone = new VldRuleNotNull(notnull)
            .and(new VldRuleNotEmpty(notempty))
            .and(
                new VldRuleStartLetter(
                    "Username shouldn't start with digit or non letter!"
                )
            )
            .and(
                new VldRuleContainsLetters(
                    "Username should contain only letters and digits!"
                )
            )
            .apply(this.login);
        final VldResult restwo = new VldRuleNotNull(notnull)
            .and(new VldRuleNotEmpty(notempty))
            .apply(this.password);
        final VldResult result;
        if (resone.valid()) {
            if (restwo.valid()) {
                result = resone;
            } else {
                result = restwo;
            }
        } else {
            result = resone;
        }
        return result;
    }
}
