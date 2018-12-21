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
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;

/**
 * Preliminary user.
 *
 * @since 1.0.0
 */
public final class PrelUser extends User {

    public enum Role {
        STUD, LECT, ADMIN
    }

    private static final Logger LOGGER = LogManager.getLogger(
            PrelUser.class.getName()
    );

    private final AppContext appContext;
    private final String login;
    private final String password;

    public PrelUser(
        final AppContext appContext, final String login, final String password
    ) {
        this.appContext = appContext;
        this.login = login;
        this.password = password;
    }

    /**
     * Checks if the user with specified login and password exists
     * in the database. The method is used during login procedure
     *
     * @return {@code true} if the user exists
     */
    @Override
    public boolean exists() {
        try (Connection con = appContext.getDataSource().getConnection();
             DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> rs = ctx.select().from(Users.USERS)
                .where(
                    Users.USERS.LOGIN.eq(login)
                        .and(Users.USERS.PASSWORD.eq(password))
                ).fetch();
            return rs.isNotEmpty();
        } catch (SQLException e) {
            LOGGER.error("Request to database failed", e);
        }
        return false;
    }

    @Override
    public void loadFields() {
        // TODO
    }

    @Override
    public void prepareJspData(HttpServletRequest request) {
        // TODO
    }

    public String path(HttpServletRequest request) {
        if (!exists()) {
            LOGGER.info("Login {} does not exist.", login);

            String validResponse = "Wrong username or password! Try again!";
            request.setAttribute("message", validResponse);
            return LOGIN_PAGE;
        }

        final User student = convertToTrue();

        HttpSession session = request.getSession();
        // TODO add login.jsp filter to check validated session
        session.setAttribute(session.getId(), student);

        student.prepareJspData(request);

        return student.getPath();
    }


    /**
     * Creates new instance of  with given login and password.
     * Login and password should match to ones in the database
     * (the method should be called after {@code userExists} method.
     *
     * @return new instance of Student, Lecturer or Administrator
     */
    @Override
    public User convertToTrue() {
        Role role = getUserRole(login, password);
        User user = new EmptyUser();
        switch (role) {
            case STUD:
                user = new Student(appContext);
                break;
            case LECT:
                user = new Lecturer(appContext);
                break;
            case ADMIN:
                user = new Admin(appContext);
                break;
        }

        user.setLogin(login);
        user.setPassword(password);
        user.loadFields();
        return user;
    }

    /**
     * Executes query to database to define user role in the system.
     *
     * @param login login
     * @param password password
     * @return {@code true} if the user exists
     */
    private Role getUserRole(String login, String password) {
        Role role = Role.STUD;
        try (Connection con = appContext.getDataSource().getConnection();
             DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> rs = ctx.select().from(Users.USERS)
                .where(
                    Users.USERS.LOGIN.eq(login)
                        .and(Users.USERS.PASSWORD.eq(password))
                ).fetch();
            if (rs.isNotEmpty()) {
                role = Role.valueOf(
                    rs.get(0).getValue(Users.USERS.ROLE).name()
                );
            }
        } catch (SQLException e) {
            LOGGER.error("Request to database failed", e);
        }
        return role;
    }
}
