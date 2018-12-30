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

import com.devproserv.courses.jooq.tables.StudentCourses;
import com.devproserv.courses.model.Db;
import com.devproserv.courses.model.Response;
import com.devproserv.courses.model.Student;
import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prepares request for further forwarding.
 *
 * @since 1.0.0
 */
abstract class CourseHandling {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseHandling.class);

    /**
     * Database.
     */
    private final Db dbase;

    /**
     * Primary constructor.
     *
     * @param dbase Database
     */
    CourseHandling(final Db dbase) {
        this.dbase = dbase;
    }

    /**
     * Returns course ID parameter.
     *
     * @return Course ID
     */
    public abstract String courseIdParameter();

    /**
     * Returns error message.
     *
     * @return Error message
     */
    public abstract String errorMessageParameter();

    /**
     * Changes entry.
     *
     * @param courseid Course ID
     * @param studentid Student ID
     */
    public abstract void changeEntry(int courseid, int studentid);

    /**
     * Returns response.
     *
     * @param courseid Course ID
     * @param student Student
     * @return Response
     */
    public Response response(final int courseid, final Student student) {
        this.changeEntry(courseid, student.getId());
        return student.response();
    }

    /**
     * Enrolls the given user to the given course.
     *
     * @param courseid Course ID
     * @param userid User ID
     */
    void insertUserCourse(final int courseid, final int userid) {
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            ctx.insertInto(
                StudentCourses.STUDENT_COURSES,
                StudentCourses.STUDENT_COURSES.COURSE_ID,
                StudentCourses.STUDENT_COURSES.STUD_ID,
                StudentCourses.STUDENT_COURSES.STATUS
            ).values(courseid, userid, "STARTED").execute();
        } catch (final SQLException exc) {
            LOGGER.error("User not inserted!", exc);
        }
    }

    /**
     * Unrolls the given user from the given course.
     *
     * @param courseid Course ID
     * @param userid User ID
     */
    void deleteUserCourse(final int courseid, final int userid) {
        try (Connection con = this.dbase.dataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            ctx.deleteFrom(StudentCourses.STUDENT_COURSES)
                .where(
                    StudentCourses.STUDENT_COURSES.COURSE_ID.eq(courseid)
                        .and(StudentCourses.STUDENT_COURSES.STUD_ID.eq(userid))
                ).execute();
        } catch (final SQLException exc) {
            LOGGER.error("User not deleted!", exc);
        }
    }
}
