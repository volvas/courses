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

import com.devproserv.courses.model.PrelUser;
import com.devproserv.courses.servlet.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;

/**
 * Represents a login form on web page
 */
public class LoginForm implements Form {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginForm.class);

    private final AppContext appContext;


    public LoginForm(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public String validate(final HttpServletRequest request) {
        final String login          = request.getParameter("login");
        final String password       = request.getParameter("password");
        final Validation validation = new LoginValidation(login, password);
        if (validation.validated()) {
            LOGGER.debug("Login '{}' and password are valid.", login);
            return validPath(request, login, password);
        } else {
            return invalidPath(validation, request, login);
        }
    }

    private String invalidPath(final Validation validation, final HttpServletRequest request, final String login) {
        LOGGER.info("Invalid credentials for login {}", login);
        request.setAttribute("message", validation.errorMessage());
        return LOGIN_PAGE;
    }

    private String validPath(final HttpServletRequest request, final String login, final String password) {
        return new PrelUser(appContext, login, password).path(request);
    }
}
