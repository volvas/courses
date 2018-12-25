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

import com.devproserv.courses.model.Db;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles sign up form.
 *
 * @since 1.0.0
 */
public final class SignUpForm implements Form {
    /**
     * Sign up JSP page file name.
     */
    public static final String SIGNUP_PAGE = "/signup.jsp";

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
        SignUpForm.class
    );

    /**
     * Database.
     */
    private final Db dbase;

    /**
     * Constructor.
     */
    public SignUpForm() {
        this(new Db());
    }

    /**
     * Primary constructor.
     * @param dbase Database
     */
    SignUpForm(final Db dbase) {
        this.dbase = dbase;
    }

    @Override
    public String validate(final HttpServletRequest request) {
        final SignupParams pars = new SignupParams(request).extract();
        final Validation validation = new SignUpValidation(pars);
        return validation.validated()
            ? this.validPath(request)
            : this.invalidPath(validation, request);
    }

    /**
     * Handles invalid path.
     * @param validation Validation
     * @param request HTTP Request
     * @return Path
     */
    private static String invalidPath(
        final Validation validation, final HttpServletRequest request
    ) {
        final String login = request.getParameter("login");
        LOGGER.info("Invalid credentials for potential login {}", login);
        request.setAttribute("message", validation.errorMessage());
        return SignUpForm.SIGNUP_PAGE;
    }

    /**
     * Handles valid path.
     * @param request HTTP Request
     * @return Path
     */
    private String validPath(final HttpServletRequest request) {
        final SignupParams pars = new SignupParams(request).extract();
        return new SignupUser(this.dbase, pars).path(request);
    }

}
