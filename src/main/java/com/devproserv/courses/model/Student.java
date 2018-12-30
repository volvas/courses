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

import com.devproserv.courses.form.EnrollForm;
import com.devproserv.courses.jooq.tables.Courses;
import com.devproserv.courses.jooq.tables.StudentCourses;
import com.devproserv.courses.jooq.tables.Users;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a student entity.
 *
 * @since 0.5.0
 */
public final class Student implements Responsible {
    /**
     * Serial number.
     */
    private static final long serialVersionUID = -6689616212027148656L;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Student.class);

    /**
     * Database.
     */
    private final Db dbase;

    /**
     * User.
     */
    private final FullNameUser user;

    /**
     * Faculty field.
     */
    private final String faculty;

    /**
     * Primary constructor.
     *
     * @param dbase Database
     * @param user Full named User
     * @param faculty Faculty
     */
    public Student(final Db dbase, final FullNameUser user, final String faculty) {
        this.dbase   = dbase;
        this.user    = user;
        this.faculty = faculty;
    }

    @Override
    public Response response() {
        final Map<String, Object> payload = new HashMap<>();
        payload.put("student", this);
        final List<Course> enrolled = this.getEnrolledCourses();
        payload.put("subscrcourses", enrolled);
        final List<Course> available = this.getAvailableCourses();
        payload.put("courses", available);
        return new Response(EnrollForm.STUDENT_PAGE, payload);
    }

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.user.getId();
    }

    /**
     * Getter.
     * @return Login
     */
    public String getLogin() {
        return this.user.getLogin();
    }

    /**
     * Getter.
     * @return Password
     */
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * Getter.
     * @return First name
     */
    public String getFirstName() {
        return this.user.getFirstName();
    }

    /**
     * Getter.
     * @return Last name
     */
    public String getLastName() {
        return this.user.getLastName();
    }

    /**
     * Getter.
     * @return Faculty
     */
    public String getFaculty() {
        return this.faculty;
    }

    /**
     * Fetches courses user has enrolled to.
     *
     * @return List of enrolled courses for the user
     */
    private List<Course> getEnrolledCourses() {
        List<Course> courses = Collections.emptyList();
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> res = ctx.select()
                .from(Courses.COURSES)
                .where(
                    Courses.COURSES.COURSE_ID.in(
                        DSL.select(StudentCourses.STUDENT_COURSES.COURSE_ID)
                        .from(StudentCourses.STUDENT_COURSES, Users.USERS)
                        .where(StudentCourses.STUDENT_COURSES.STUD_ID
                            .eq(Users.USERS.USER_ID)
                            .and(Users.USERS.LOGIN.eq(this.getLogin()))
                        )
                    )
                )
                .fetch();
            courses = res.stream()
                .map(Student::makeCourse)
                .collect(Collectors.toList());
        } catch (final SQLException ex) {
            LOGGER.error("Query for enrolled courses failed.", ex);
        }
        return courses;
    }

    /**
     * Fetches available courses user cab enroll to.
     *
     * @return List of available courses
     */
    private List<Course> getAvailableCourses() {
        List<Course> courses = Collections.emptyList();
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            final Result<Record> res = ctx.select()
                .from(Courses.COURSES)
                .where(
                    Courses.COURSES.COURSE_ID.notIn(
                        DSL.select(StudentCourses.STUDENT_COURSES.COURSE_ID)
                        .from(StudentCourses.STUDENT_COURSES, Users.USERS)
                        .where(StudentCourses.STUDENT_COURSES.STUD_ID
                            .eq(Users.USERS.USER_ID)
                            .and(Users.USERS.LOGIN.eq(this.getLogin()))
                        )
                    )
                )
                .fetch();
            courses = res.stream()
                .map(Student::makeCourse)
                .collect(Collectors.toList());
        } catch (final SQLException ex) {
            LOGGER.error("Query for available courses failed.", ex);
        }
        return courses;
    }

    /**
     * Makes Course.
     * @param rec Record
     * @return Course
     */
    private static Course makeCourse(final Record rec) {
        return new Course(
            rec.getValue(Courses.COURSES.COURSE_ID),
            rec.getValue(Courses.COURSES.NAME),
            rec.getValue(Courses.COURSES.DESCRIPTION)
        );
    }
}
