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

import com.devproserv.courses.form.SignupParams;
import com.devproserv.courses.form.SignupUser;
import com.devproserv.courses.model.Db;
import com.devproserv.courses.model.Response;
import com.devproserv.courses.validation.results.VldResult;
import com.devproserv.courses.validation.results.VldResultAggr;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a new user using data from the HTTP request.
 * And stores the data in the database
 *
 * @since 1.0.0
 */
public final class SignUp implements Command {
    /**
     * Sign up JSP page file name.
     */
    public static final String SIGNUP_PAGE = "/signup.jsp";

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SignUp.class);

    /**
     * Database.
     */
    private final Db dbase;

    /**
     * Constructor.
     */
    SignUp() {
        this(new Db());
    }

    /**
     * Primary constructor.
     *
     * @param dbase Database
     */
    SignUp(final Db dbase) {
        this.dbase = dbase;
    }

    @Override
    public Response response(final HttpServletRequest request) {
        final String login    = request.getParameter("login");
        final String password = request.getParameter("password");
        final String fname    = request.getParameter("firstname");
        final String lname    = request.getParameter("lastname");
        final String faculty  = request.getParameter("faculty");
        final VldResult result = new VldResultAggr()
            .checkUsername(login)
            .checkPassword(password)
            .checkFieldNotNullEmpty(fname, "First name")
            .checkFieldNotNullEmpty(lname, "Last name")
            .checkFieldNotNullEmpty(faculty, "Faculty")
            .aggregate();
        final Response response;
        if (result.valid()) {
            response = this.validPath(request);
        } else {
            response = invalidPath(result, login);
        }
        return response;
    }

    /**
     * Handles invalid path.
     *
     * @param result Validation result
     * @param login Login name
     * @return Response
     */
    private static Response invalidPath(final VldResult result, final String login) {
        LOGGER.info("Invalid credentials for potential login {}", login);
        final Map<String, Object> payload = new HashMap<>();
        payload.put("message", result.reason().orElse(""));
        return new Response(SignUp.SIGNUP_PAGE, payload);
    }

    /**
     * Handles valid path.
     *
     * @param request HTTP Request
     * @return Response
     */
    private Response validPath(final HttpServletRequest request) {
        final SignupParams pars = new SignupParams(request).extract();
        return new SignupUser(this.dbase, pars).response();
    }
}
