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
 * @since 0.5.0
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
    private String login;

    /**
     * Password.
     */
    private String pass;

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
    private final Map<UserRole, Supplier<Nest>> roles;

    /**
     * Constructor.
     */
    public UserRoles() {
        this(new Db());
    }

    /**
     * Primary constructor.
     *
     * @param dbase Database
     */
    UserRoles(final Db dbase) {
        this.dbase = dbase;
        this.roles = new EnumMap<>(UserRole.class);
    }

    /**
     * Builder.
     *
     * @param username Login
     * @param password Password
     * @return This instance
     */
    public UserRoles build(final String username, final String password) {
        this.login = username;
        this.pass  = password;
        this.roles.put(UserRole.STUD, () -> new NestStudents(this.dbase, username, password));
        this.roles.put(UserRole.LECT, NestLecturers::new);
        this.roles.put(UserRole.ADMIN, NestAdmins::new);
        return this;
    }

    /**
     * Defines the path.
     *
     * @param request HTTP request
     * @return Response
     */
    public Response response(final HttpServletRequest request) {
        final Responsible user = this.makeUser();
        final Response response = user.response();
        final HttpSession session = request.getSession();
        session.setAttribute(session.getId(), user);
        return response;
    }

    /**
     * Returns an user instance on base of login and password.
     *
     * @return Responsible instance
     */
    private Responsible makeUser() {
        Responsible user = new EmptyUser();
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> res = ctx.select().from(Users.USERS)
                .where(
                    Users.USERS.LOGIN.eq(this.login)
                    .and(Users.USERS.PASSWORD.eq(this.pass))
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
     * @return Responsible instance
     */
    private Responsible fetchUser(final Result<Record> res) {
        final Responsible user;
        if (res.isNotEmpty()) {
            final UserRole role = UserRole.valueOf(res.get(0).getValue(Users.USERS.ROLE).name());
            final Nest nest = this.roles.get(role).get();
            user = nest.makeUser();
        } else {
            user = new EmptyUser();
        }
        return user;
    }
}
