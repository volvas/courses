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
import com.devproserv.courses.jooq.tables.Students;
import com.devproserv.courses.jooq.tables.Users;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a student entity.
 *
 * @since 1.0.0
 */
public final class Student extends User {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Student.class);

    /**
     * Database.
     */
    private final Db dbase;

    /**
     * Faculty field.
     */
    private String faculty;

    /**
     * Constructor.
     *
     * @param dbase Database
     * @param login Login
     * @param password Password
     */
    public Student(final Db dbase, final String login, final String password) {
        this(dbase, login, password, null, null, null);
    }

    /**
     * Primary constructor.
     *
     * @param dbase Database
     * @param login Login
     * @param password Password
     * @param fname First name
     * @param lname Last name
     * @param faculty Faculty
     */
    public Student(
        final Db dbase, final String login, final String password,
        final String fname, final String lname, final String faculty
    ) {
        super(login, password);
        this.dbase = dbase;
        setFirstName(fname);
        setLastName(lname);
        this.faculty = faculty;
    }

    @Override
    public void loadFields() {
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
                .where(Users.USERS.LOGIN.eq(this.getLogin()))
                .fetch();
            res.forEach(
                r -> {
                    this.setId(r.value1());
                    this.setFirstName(r.value2());
                    this.setLastName(r.value3());
                    this.setFaculty(r.value4());
                });
        } catch (final SQLException ex) {
            LOGGER.error("Field loading failed!", ex);
        }
    }

    @Override
    public void prepareJspData(final HttpServletRequest request) {
        request.setAttribute("student", this);
        final List<Course> enrolled = this.getEnrolledCourses();
        request.setAttribute("subscrcourses", enrolled);
        final List<Course> available = this.getAvailableCourses();
        request.setAttribute("courses", available);
    }

    @Override
    public Response response() {
        this.loadFields();
        final Map<String, Object> payload = new HashMap<>();
        payload.put("student", this);
        final List<Course> subscribed = this.getEnrolledCourses();
        payload.put("subscrcourses", subscribed);
        final List<Course> available = this.getAvailableCourses();
        payload.put("courses", available);
        return new Response(EnrollForm.STUDENT_PAGE, payload);
    }

    /**
     * Getter.
     * @return Faculty
     */
    public String getFaculty() {
        return this.faculty;
    }

    /**
     * Setter.
     * @param fac Faculty
     */
    private void setFaculty(final String fac) {
        this.faculty = fac;
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
                            .and(Users.USERS.LOGIN.eq(getLogin()))
                        )
                    )
                )
                .fetch();
            courses = res.stream()
                .map(this::makeCourse)
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
                .map(this::makeCourse)
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
    private Course makeCourse(final Record rec) {
        final Course course = new Course(this.dbase);
        course.setId(rec.getValue(Courses.COURSES.COURSE_ID));
        course.setName(rec.getValue(Courses.COURSES.NAME));
        course.setDescription(rec.getValue(Courses.COURSES.DESCRIPTION));
        return course;
    }
}
