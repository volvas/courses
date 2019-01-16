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
package com.devproserv.courses.command;

import com.devproserv.courses.form.EnrollForm;
import com.devproserv.courses.model.Response;
import com.devproserv.courses.model.UserRoles;
import com.devproserv.courses.validation.results.VldResult;
import com.devproserv.courses.validation.results.VldResultAggr;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Treats data from login web form.
 *
 * @since 0.5.0
 */
public final class Login implements Command {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    /**
     * Validation rule aggregator.
     */
    private final VldResultAggr aggregator;

    /**
     * Default constructor.
     */
    public Login() {
        this(new VldResultAggr());
    }

    /**
     * Primary constructor.
     *
     * @param aggregator Validation rule aggregator
     */
    public Login(final VldResultAggr aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public Response response(final HttpServletRequest request) {
        final String login     = request.getParameter("login");
        final String password  = request.getParameter("password");
        final VldResult result = this.aggregator
            .checkUsername(login)
            .checkPassword(password)
            .aggregate();
        final Response response;
        if (result.valid()) {
            response = validPath(request, login, password);
        } else {
            response = invalidPath(result, login);
        }
        return response;
    }

    /**
     * Handles invalid path.
     * @param result Validation result
     * @param login Login
     * @return Response
     */
    private static Response invalidPath(final VldResult result, final String login) {
        LOGGER.info("Invalid credentials for login {}", login);
        final Map<String, Object> payload = new HashMap<>();
        payload.put("message", result.reason().orElse(""));
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
        final HttpServletRequest request, final String login, final String password
    ) {
        LOGGER.debug("Login '{}' and password are valid.", login);
        return new UserRoles(login, password).build().response(request);
    }
}
