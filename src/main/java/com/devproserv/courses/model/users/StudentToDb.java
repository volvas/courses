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

package com.devproserv.courses.model.users;

import com.devproserv.courses.command.SignUp;
import com.devproserv.courses.form.EnrollForm;
import com.devproserv.courses.jooq.enums.UsersRole;
import com.devproserv.courses.jooq.tables.Students;
import com.devproserv.courses.jooq.tables.Users;
import com.devproserv.courses.jooq.tables.records.UsersRecord;
import com.devproserv.courses.model.Db;
import com.devproserv.courses.model.Response;
import com.devproserv.courses.model.Responsible;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a student entity that is saved in DB.
 *
 * @since 0.5.3
 */
public final class StudentToDb implements Responsible {
    /**
     * Serial number.
     */
    private static final long serialVersionUID = 7763160453599358140L;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentToDb.class);

    /**
     * Database.
     */
    private transient Db dbase;

    /**
     * Student.
     */
    private final Student student;

    /**
     * Primary constructor.
     *
     * @param dbase Database
     * @param student Student
     */
    public StudentToDb(final Db dbase, final Student student) {
        this.dbase = dbase;
        this.student = student;
    }

    @Override
    public Response response() {
        final String attribute = "message";
        final String path;
        final Map<String, Object> payload = new HashMap<>();
        if (this.loginExists()) {
            payload.put(attribute, "User already exists!");
            path = SignUp.SIGNUP_PAGE;
        } else if (this.insertUser()) {
            path = EnrollForm.LOGIN_PAGE;
        } else {
            payload.put(attribute, "User has not been created. Try again.");
            path = SignUp.SIGNUP_PAGE;
        }
        return new Response(path, payload);
    }

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.student.getId(); }

    /**
     * Getter.
     * @return Login
     */
    public String getLogin() {
        return this.student.getLogin();
    }

    /**
     * Getter.
     * @return Password
     */
    public String getPassword() {
        return this.student.getPassword();
    }

    /**
     * Getter.
     * @return First name
     */
    public String getFirstName() {
        return this.student.getFirstName();
    }

    /**
     * Getter.
     * @return Last name
     */
    public String getLastName() {
        return this.student.getLastName();
    }

    /**
     * Getter.
     * @return Faculty
     */
    public String getFaculty() {
        return this.student.getFaculty();
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
                .where(Users.USERS.LOGIN.eq(this.student.getLogin()))
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
                Users.USERS.LOGIN, Users.USERS.PASSWORD, Users.USERS.ROLE
            ).values(
                this.student.getFirstName(), this.student.getLastName(), this.student.getLogin(),
                this.student.getPassword(), UsersRole.STUD
            ).returning(Users.USERS.USER_ID).fetch();
            final int id = res.get(0).getValue(Users.USERS.USER_ID);
            final int inserted = ctx.insertInto(
                Students.STUDENTS, Students.STUDENTS.STUD_ID, Students.STUDENTS.FACULTY
            ).values(id, this.student.getFaculty()).execute();
            success = inserted != 0;
        } catch (final SQLException ex) {
            LOGGER.error("Inserting user to DB failed!", ex);
        }
        return success;
    }

    /**
     * Overrides deserialization method to meet findbugs requirements.
     *
     * @param ois ObjectInputStream
     * @throws IOException IO exception
     * @throws ClassNotFoundException Class not found exception
     */
    private void readObject(
        final ObjectInputStream ois
    ) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.dbase = new Db();
    }
}
