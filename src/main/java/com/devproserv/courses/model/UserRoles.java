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

package com.devproserv.courses.model;

import com.devproserv.courses.jooq.tables.Users;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls user in accordance to their role.
 *
 * @since 1.0.0
 */
public class UserRoles {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoles.class);

    /**
     * Database.
     */
    private final Db dbase;

    /**
     * Login.
     */
    private final String login;

    /**
     * Password.
     */
    private final String password;

    /**
     * Enum Roles.
     */
    private enum UserRole {
        /**
         * Student role.
         */
        STUD,

        /**
         * Lecturer role.
         */
        LECT,

        /**
         * Administrator role.
         */
        ADMIN
    }

    /**
     * Roles.
     */
    private final Map<UserRole, Supplier<User>> roles;

    /**
     * Constructor.
     *
     * @param login Login
     * @param password Password
     */
    public UserRoles(final String login, final String password) {
        this(new Db(), login, password);
    }

    /**
     * Primary constructor.
     *
     * @param dbase Database
     * @param login Login
     * @param password Password
     */
    UserRoles(final Db dbase, final String login, final String password) {
        this.dbase    = dbase;
        this.login    = login;
        this.password = password;
        this.roles    = new EnumMap<>(UserRole.class);
    }

    /**
     * Builder.
     * @return This instance
     */
    public UserRoles build() {
        this.roles.put(UserRole.STUD, () -> new Student(this.dbase, this.login, this.password));
        this.roles.put(UserRole.LECT, () -> new Lecturer(this.login, this.password));
        this.roles.put(UserRole.ADMIN, () -> new Admin(this.login, this.password));
        return this;
    }

    /**
     * Defines the path.
     *
     * @param request HTTP request
     * @return Response
     */
    public Response path(final HttpServletRequest request) {
        final User user = this.user();
        final Response response = user.response();
        final HttpSession session = request.getSession();
        session.setAttribute(session.getId(), user);
        return response;
    }

    /**
     * Returns an user instance on base of login and password.
     *
     * @return User instance
     */
    private User user() {
        User user = new EmptyUser(this.login, this.password);
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> res = ctx.select().from(Users.USERS)
                .where(
                    Users.USERS.LOGIN.eq(this.login)
                    .and(Users.USERS.PASSWORD.eq(this.password))
                ).fetch();
            user = this.fetchUser(res);
        } catch (final SQLException ex) {
            LOGGER.error("Fetching user failed.", ex);
        }
        return user;
    }

    /**
     * Fetches user from the result set.
     *
     * @param res Result
     * @return User instance
     */
    private User fetchUser(final Result<Record> res) {
        final User user;
        if (res.isNotEmpty()) {
            final UserRole role = UserRole.valueOf(res.get(0).getValue(Users.USERS.ROLE).name());
            user = this.roles.get(role).get();
        } else {
            user = new EmptyUser(this.login, this.password);
        }
        return user;
    }
}
