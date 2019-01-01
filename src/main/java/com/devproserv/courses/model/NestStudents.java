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

import com.devproserv.courses.jooq.tables.Students;
import com.devproserv.courses.jooq.tables.Users;
import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deals with students.
 *
 * @since 0.5.0
 */
public final class NestStudents implements Nest {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NestStudents.class);

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
     * Primary constructor.
     *
     * @param dbase Database
     * @param login Login
     * @param password Password
     */
    NestStudents(final Db dbase, final String login, final String password) {
        this.dbase = dbase;
        this.login = login;
        this.password = password;
    }

    @Override
    public Responsible makeUser() {
        Responsible user = new EmptyUser();
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record4<Integer, String, String, String>> res = ctx
                .select(
                    Users.USERS.USER_ID, Users.USERS.FIRSTNAME,
                    Users.USERS.LASTNAME, Students.STUDENTS.FACULTY
                )
                .from(Users.USERS)
                .join(Students.STUDENTS)
                .on(Users.USERS.USER_ID.eq(Students.STUDENTS.STUD_ID))
                .where(Users.USERS.LOGIN.eq(this.login).and(Users.USERS.PASSWORD.eq(this.password)))
                .fetch();
            user = res.stream()
                .map(
                    r -> (Responsible) new Student(
                        new NestCourses(this.dbase), new FullNameUser(
                            new User(r.value1(), this.login, this.password),
                            r.value2(), r.value3()
                        ), r.value4()
                    )
                )
                .findFirst().orElse(user);
        } catch (final SQLException ex) {
            LOGGER.error("Field loading failed!", ex);
        }
        return user;
    }
}
