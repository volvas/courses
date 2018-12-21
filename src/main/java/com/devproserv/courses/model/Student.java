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

import com.devproserv.courses.config.MainConfig;
import com.devproserv.courses.jooq.enums.UsersRole;
import com.devproserv.courses.jooq.tables.Courses;
import com.devproserv.courses.jooq.tables.StudentCourses;
import com.devproserv.courses.jooq.tables.Students;
import com.devproserv.courses.jooq.tables.Users;
import com.devproserv.courses.jooq.tables.records.UsersRecord;
import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.select;

/**
 * Represents a student entity
 *
 * @since 1.0.0
 */
public final class Student extends User {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            Student.class.getName()
    );

    /**
     * Application context.
     */
    private final AppContext context;

    /**
     * Faculty field.
     */
    private String faculty;

    /**
     * Constructor.
     * @param context Application context
     */
    public Student(final AppContext context) {
        this.context = context;
    }

    /**
     * Constructor.
     * @param context Application context
     * @param login Login
     * @param password Password
     * @param firstName First name
     * @param lastName Last name
     * @param faculty Faculty
     */
    public Student(
            final AppContext context, final String login, final String password,
            final String firstName, final String lastName, final String faculty
    ) {
        this.context = context;
        setLogin(login);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        this.faculty = faculty;
    }

    @Override
    public boolean exists() {
        return true; // TODO
    }

    @Override
    public void loadFields() {
        try (Connection con = context.getDataSource().getConnection();
             DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record4<Integer, String, String, String>> rs = ctx
                .select(
                    Users.USERS.USER_ID, Users.USERS.FIRSTNAME,
                    Users.USERS.LASTNAME, Students.STUDENTS.FACULTY
                )
                .from(Users.USERS)
                .join(Students.STUDENTS)
                .on(Users.USERS.USER_ID.eq(Students.STUDENTS.STUD_ID))
                .where(Users.USERS.LOGIN.eq(getLogin()))
                .fetch();

            rs.forEach(r -> {
                    setId       (r.value1());
                    setFirstName(r.value2());
                    setLastName (r.value2());
                    setFaculty  (r.value2());
            });
        } catch (SQLException e) {
            LOGGER.error("Request to database failed", e);
        }
        setPath(MainConfig.STUDENT_PAGE);
    }

    public String path(final HttpServletRequest request) {
        if (loginExists()) {
            request.setAttribute("message", "User already exists!");
            return MainConfig.SIGNUP_PAGE;
        } else if (insertUser()) {
            return MainConfig.LOGIN_PAGE;
        } else {
            request.setAttribute("message",
                "User has not been created. Try again."
            );
            return MainConfig.SIGNUP_PAGE;
        }
    }

    /**
     * Prepares data to be displayed on the own student's page and
     * attaches the data to HTTP request.
     * Then JSP servlet will handle this request.
     *
     * @param request HTTP request
     */
    @Override
    public void prepareJspData(final HttpServletRequest request) {
        request.setAttribute("student", this);
        final List<Course> subscribedCourses = getSubscribedCourses();
        request.setAttribute("subscrcourses", subscribedCourses);
        final List<Course> availableCourses = getAvailableCourses();
        request.setAttribute("courses", availableCourses);
    }

    /**
     * Executes request into the database for getting the courses that a student
     * has been subscribed to.
     *
     * @return list of subscribed courses for the user
     */
    private List<Course> getSubscribedCourses() {
        try (Connection con = context.getDataSource().getConnection();
             DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> rs = ctx.select()
                .from(Courses.COURSES)
                .where(Courses.COURSES.COURSE_ID.in(
                    select(StudentCourses.STUDENT_COURSES.COURSE_ID)
                    .from(StudentCourses.STUDENT_COURSES, Users.USERS)
                    .where(StudentCourses.STUDENT_COURSES.STUD_ID
                            .eq(Users.USERS.USER_ID)
                            .and(Users.USERS.LOGIN.eq(getLogin()))
                    )
                ))
                .fetch();
            return rs.stream()
                .map(r -> {
                final Course course = new Course(context);
                course.setId         (r.getValue(Courses.COURSES.COURSE_ID));
                course.setName       (r.getValue(Courses.COURSES.NAME));
                course.setDescription(r.getValue(Courses.COURSES.DESCRIPTION));
                return course;
                })
                .collect(Collectors.toList());
        } catch (SQLException e) {
            LOGGER.error("Request to database failed", e);
        }
        return Collections.emptyList();
    }

    /**
     * Executes request into the database and returns list of courses that are
     * available for the current student to subscribe.
     *
     * @return list of available courses from the database
     */
    private List<Course> getAvailableCourses() {
        try (Connection con = context.getDataSource().getConnection();
             DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> rs = ctx.select()
                .from(Courses.COURSES)
                .where(Courses.COURSES.COURSE_ID.notIn(
                    select(StudentCourses.STUDENT_COURSES.COURSE_ID)
                        .from(StudentCourses.STUDENT_COURSES, Users.USERS)
                        .where(StudentCourses.STUDENT_COURSES.STUD_ID
                            .eq(Users.USERS.USER_ID)
                            .and(Users.USERS.LOGIN.eq(getLogin()))
                        )
                ))
                .fetch();
            return rs.stream()
                .map(r -> {
                    final Course course = new Course(context);
                    course.setId         (r.getValue(Courses.COURSES.COURSE_ID));
                    course.setName       (r.getValue(Courses.COURSES.NAME));
                    course.setDescription(r.getValue(Courses.COURSES.DESCRIPTION));
                    return course;
                })
                .collect(Collectors.toList());
        } catch (SQLException e) {
            LOGGER.error("Request to database failed", e);
        }
        return Collections.emptyList();
    }


    /**
     * Checks if the specified login exists in the database.
     * The method is used during sign up procedure.
     *
     * @return {@code true} if the user exists and {@code false} if does not
     */
    private boolean loginExists() {
        try (Connection con = context.getDataSource().getConnection();
             DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> rs = ctx.select()
                .from(Users.USERS)
                .where(Users.USERS.LOGIN.eq(getLogin()))
                .fetch();
            return rs.isNotEmpty();
        } catch (SQLException e) {
            LOGGER.error("Request to database failed", e);
        }
        return true; // less changes in the database if something is wrong
    }

    /**
     * Executes request into the database (tables 'users' and 'students')
     * to insert the current user.
     *
     * @return {@code true} if the user has been created successfully
     * and {@code false} if is not.
     */
    private boolean insertUser() {
        try (Connection con = context.getDataSource().getConnection();
             DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<UsersRecord> rs = ctx.insertInto(
                Users.USERS, Users.USERS.FIRSTNAME, Users.USERS.LASTNAME,
                    Users.USERS.LOGIN, Users.USERS.PASSWORD,
                    Users.USERS.ROLE
                )
                .values(
                    getFirstName(), getLastName(), getLogin(), getPassword(),
                    UsersRole.STUD
                )
                .returning(Users.USERS.USER_ID)
                .fetch();
            setId(rs.get(0).getValue(Users.USERS.USER_ID));
            final int insertingResult = ctx.insertInto(
                Students.STUDENTS, Students.STUDENTS.STUD_ID,
                Students.STUDENTS.FACULTY
            )
            .values(getId(), getFaculty())
            .execute();
            return insertingResult != 0;
        } catch (SQLException e) {
            LOGGER.error("Request to database failed", e);
        }
        return false;
    }

    public String getFaculty() {
        return faculty;
    }

    private void setFaculty(final String faculty) {
        this.faculty = faculty;
    }
}
