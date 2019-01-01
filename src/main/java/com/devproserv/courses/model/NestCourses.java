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

import com.devproserv.courses.jooq.tables.Courses;
import com.devproserv.courses.jooq.tables.StudentCourses;
import com.devproserv.courses.jooq.tables.Users;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deals with courses.
 *
 * @since 0.5.1
 */
final class NestCourses {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NestCourses.class);

    /**
     * Database.
     */
    private final Db dbase;

    /**
     * Primary constructor.
     *
     * @param dbase Database
     */
    NestCourses(final Db dbase) {
        this.dbase = dbase;
    }

    /**
     * Fetches courses user with the given login has enrolled to.
     *
     * @param login Login
     * @return List of enrolled courses for the user
     */
    List<Course> getEnrolledCourses(final String login) {
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
                                .and(Users.USERS.LOGIN.eq(login))
                            )
                    )
                )
                .fetch();
            courses = res.stream()
                .map(NestCourses::makeCourse)
                .collect(Collectors.toList());
        } catch (final SQLException ex) {
            LOGGER.error("Query for enrolled courses failed.", ex);
        }
        return courses;
    }

    /**
     * Fetches available courses user with the given login can enroll to.
     *
     * @param login Login
     * @return List of available courses
     */
    List<Course> getAvailableCourses(final String login) {
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
                                .and(Users.USERS.LOGIN.eq(login))
                            )
                    )
                )
                .fetch();
            courses = res.stream()
                .map(NestCourses::makeCourse)
                .collect(Collectors.toList());
        } catch (final SQLException ex) {
            LOGGER.error("Query for available courses failed.", ex);
        }
        return courses;
    }

    /**
     * Makes Course.
     *
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
