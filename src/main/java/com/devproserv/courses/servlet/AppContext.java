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

package com.devproserv.courses.servlet;

import com.devproserv.courses.command.Command;
import com.devproserv.courses.command.Enroll;
import com.devproserv.courses.command.Login;
import com.devproserv.courses.command.Logout;
import com.devproserv.courses.command.NotFound;
import com.devproserv.courses.command.SignUp;
import com.devproserv.courses.command.Unroll;
import com.devproserv.courses.config.Conf;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code AppContext} is a main container controls application lifecycle.
 *
 * @since 1.0.0
 */
public class AppContext {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
        AppContext.class
    );

    /**
     * DB URL.
     */
    private static final String DB_URL = "java:comp/env/jdbc/coursedb";

    /**
     * DataSource.
     */
    private DataSource source;

    /**
     * Commands.
     */
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Constructor.
     */
    AppContext() {
        this.commands.put(Conf.COMMAND_SIGNUP, new SignUp(this));
        this.commands.put(Conf.COMMAND_LOGIN, new Login(this));
        this.commands.put(Conf.COMMAND_LOGOUT, new Logout());
        this.commands.put(Conf.COMMAND_SUBSCRIBE, new Enroll(this));
        this.commands.put(Conf.COMMAND_UNROLL, new Unroll(this));
        try {
            final InitialContext ctx = new InitialContext();
            this.source = (DataSource) ctx.lookup(AppContext.DB_URL);
        } catch (final NamingException exc) {
            LOGGER.error(
                "Database with path {} not found! Error: {}",
                AppContext.DB_URL, exc
            );
        }
        LOGGER.debug("Beans initialized.");
    }

    /**
     * Getter.
     * @return DataSource
     */
    public DataSource getDataSource() {
        return this.source;
    }

    /**
     * Delivers a path of a page defined by given request.
     *
     * @param request HTTP request
     * @return String with a path defined by parameter "command" in request
     */
    public String getPath(final HttpServletRequest request) {
        final String par = request.getParameter("command");
        Command command = this.commands.get(par);
        if (command == null) {
            LOGGER.warn("Invalid command given!");
            command = new NotFound();
        }
        return command.path(request);
    }
}
