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

import com.devproserv.courses.model.Student;
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Handles sign up form
 */
public class SignUpForm implements Form {
    /**
     * Sign up JSP page file name.
     */
    public static final String SIGNUP_PAGE = "/signup.jsp";
    /**
     * Logger.
     */
    private static final Logger logger = LogManager.getLogger(SignUpForm.class.getName());

    /**
     * Application context.
     */
    private final AppContext context;

    /**
     * Constructor.
     * @param context Application context
     */
    public SignUpForm(final AppContext context) {
        this.context = context;
    }

    @Override
    public String validate(final HttpServletRequest request) {
        final String login      = request.getParameter("login");
        final String password   = request.getParameter("password");
        final String firstName  = request.getParameter("firstname");
        final String lastName   = request.getParameter("lastname");
        final String faculty    = request.getParameter("faculty");
        final Validation validation = new SignUpValidation(
            login, password, firstName, lastName, faculty
        );
        return validation.validated()
            ? validPath(request)
            : invalidPath(validation, request);
    }

    private String invalidPath(
            final Validation validation, final HttpServletRequest request
    ) {
        final String login = request.getParameter("login");
        logger.info("Invalid credentials for potential login " + login);
        request.setAttribute("message", validation.errorMessage());
        return SIGNUP_PAGE;
    }

    private String validPath(final HttpServletRequest request) {
        final String login      = request.getParameter("login");
        final String password   = request.getParameter("password");
        final String firstName  = request.getParameter("firstname");
        final String lastName   = request.getParameter("lastname");
        final String faculty    = request.getParameter("faculty");
        return new Student(
            context, login, password, firstName, lastName, faculty
        ).path(request);
    }
}
