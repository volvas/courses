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
        final String notnull = "Username and password should not be null!";
        final String notempty = "Username and password should not be empty!";
        final VldResult result;
        final VldResult resone = new VldRuleNotNull<String>(notnull)
            .and(new VldRuleNotEmpty(notempty))
            .and(
            new VldRuleStartLetter(
                "Username shouldn't start with digit or non letter!"
            )
        ).and(
            new VldRuleContainsLetters(
                "Username should contain only letters and digits!"
            )
        ).apply(this.login);
        final VldResult restwo = new VldRuleNotNull<String>(notnull)
            .and(new VldRuleNotEmpty(notempty))
            .apply(this.password);
        final VldResult resthree = new VldRuleNotNull<String>(
            "First name should not be null!"
        ).and(new VldRuleNotEmpty("First name should not be empty!"))
            .apply(this.fname);
        final VldResult resfour = new VldRuleNotNull<String>(
            "Last name should not be null!"
        ).and(new VldRuleNotEmpty("Last name should not be empty!"))
            .apply(this.lname);
        final VldResult resfive = new VldRuleNotNull<String>(
            "Faculty should not be null!"
        ).and(new VldRuleNotEmpty("Faculty should not be empty!"))
            .apply(this.faculty);
        if (!resone.valid()) {
            result = resone;
        } else if (!restwo.valid()) {
            result = restwo;
        } else if (!resthree.valid()) {
            result = resthree;
        } else if (!resfour.valid()) {
            result = resfour;
        } else if (!resfive.valid()) {
            result = resfive;
        } else {
            result = resone;
        }
        return result;
    }
}
