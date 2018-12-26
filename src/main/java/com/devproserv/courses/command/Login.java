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
package com.devproserv.courses.command;

import com.devproserv.courses.form.EnrollForm;
import com.devproserv.courses.form.LoginValidation;
import com.devproserv.courses.form.Validation;
import com.devproserv.courses.model.Response;
import com.devproserv.courses.model.UserRoles;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Treats data from login web form.
 *
 * @since 1.0.0
 */
public final class Login implements Command {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    @Override
    public Response response(final HttpServletRequest request) {
        final String login          = request.getParameter("login");
        final String password       = request.getParameter("password");
        final Validation validation = new LoginValidation(login, password);
        final Response response;
        if (validation.validated()) {
            LOGGER.debug("Login '{}' and password are valid.", login);
            response = validPath(request, login, password);
        } else {
            response = invalidPath(validation, login);
        }
        return response;
    }

    /**
     * Handles invalid path.
     * @param validation Validation
     * @param login Login
     * @return Response
     */
    private static Response invalidPath(
        final Validation validation, final String login
    ) {
        LOGGER.info("Invalid credentials for login {}", login);
        final Map<String, Object> payload = new HashMap<>();
        payload.put("message", validation.errorMessage());
        return new Response(EnrollForm.LOGIN_PAGE, payload);
    }

    /**
     * Handles valid path.
     * @param request HTTP Request
     * @param login Login
     * @param password Password
     * @return Path
     */
    private static Response validPath(
        final HttpServletRequest request, final String login,
        final String password
    ) {
        return new UserRoles(login, password).build().path(request);
    }
}
