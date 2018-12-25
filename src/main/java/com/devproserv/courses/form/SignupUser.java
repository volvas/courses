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

import com.devproserv.courses.jooq.enums.UsersRole;
import com.devproserv.courses.jooq.tables.Students;
import com.devproserv.courses.jooq.tables.Users;
import com.devproserv.courses.jooq.tables.records.UsersRecord;
import com.devproserv.courses.model.Db;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performs sign up process.
 *
 * @since 1.0.0
 */
public final class SignupUser {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
        SignupUser.class
    );

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
     * First name.
     */
    private final String fname;

    /**
     * Last name.
     */
    private final String lname;

    /**
     * Faculty field.
     */
    private final String faculty;

    /**
     * Primary constructor.
     *
     * @param dbase Database
     * @param pars Sign up parameters
     */
    SignupUser(final Db dbase, final SignupParams pars) {
        this.dbase    = dbase;
        this.login    = pars.getLogin();
        this.password = pars.getPassword();
        this.fname    = pars.getFirstName();
        this.lname    = pars.getLastName();
        this.faculty  = pars.getFaculty();
    }

    /**
     * Provides path after creating new user.
     * @param request HTTP request
     * @return Path
     */
    public String path(final HttpServletRequest request) {
        final String attribute = "message";
        final String path;
        if (this.loginExists()) {
            request.setAttribute(attribute, "User already exists!");
            path = SignUpForm.SIGNUP_PAGE;
        } else if (this.insertUser()) {
            path = EnrollForm.LOGIN_PAGE;
        } else {
            request.setAttribute(
                attribute, "User has not been created. Try again."
            );
            path = SignUpForm.SIGNUP_PAGE;
        }
        return path;
    }

    /**
     * Checks if the specified login exists in the database.
     *
     * @return True if the user exists
     */
    private boolean loginExists() {
        boolean exists = true;
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> res = ctx.select()
                .from(Users.USERS)
                .where(Users.USERS.LOGIN.eq(this.login))
                .fetch();
            exists = res.isNotEmpty();
        } catch (final SQLException ex) {
            LOGGER.error("SQL query during checking login exists failed!", ex);
        }
        return exists;
    }

    /**
     * Executes request into the database (tables 'users' and 'students')
     * to insert the current user.
     *
     * @return True if the user has been created successfully
     */
    private boolean insertUser() {
        boolean success = false;
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<UsersRecord> res = ctx.insertInto(
                Users.USERS, Users.USERS.FIRSTNAME, Users.USERS.LASTNAME,
                Users.USERS.LOGIN, Users.USERS.PASSWORD,
                Users.USERS.ROLE
            ).values(
                this.fname, this.lname, this.login, this.password,
                UsersRole.STUD
            ).returning(Users.USERS.USER_ID).fetch();
            final int id = res.get(0).getValue(Users.USERS.USER_ID);
            final int inserted = ctx.insertInto(
                Students.STUDENTS, Students.STUDENTS.STUD_ID,
                Students.STUDENTS.FACULTY
            ).values(id, this.faculty).execute();
            success = inserted != 0;
        } catch (final SQLException ex) {
            LOGGER.error("Inserting user to DB failed!", ex);
        }
        return success;
    }
}
