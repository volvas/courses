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

import com.devproserv.courses.jooq.tables.StudentCourses;
import com.devproserv.courses.servlet.AppContext;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * Represents the entity of Course. Maps the table 'courses' in the database.
 * Part of DAO design pattern.
 *
 * @since 1.0.0
 */
public class Course {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
        Course.class.getName()
    );

    /**
     * Application context.
     */
    private final AppContext context;

    /**
     * ID.
     */
    private int id;

    /**
     * Name.
     */
    private String name;

    /**
     * Description.
     */
    private String description;

    /**
     * Constructor.
     * @param context Application context
     */
    public Course(final AppContext context) {
        this.context = context;
    }

    /**
     * Executes request into the database (table 'student_courses') to insert
     * the current user and course. In other words, the current user subscribes
     * to the current course
     *
     * @param user Current user
     */
    public void insertUserCourse(final User user) {
        try (Connection con = this.context.getDataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            ctx.insertInto(
                StudentCourses.STUDENT_COURSES,
                StudentCourses.STUDENT_COURSES.COURSE_ID,
                StudentCourses.STUDENT_COURSES.STUD_ID,
                StudentCourses.STUDENT_COURSES.STATUS
            )
            .values(
                this.getId(),
                user.getId(),
                "STARTED"
            ).execute();
        } catch (final SQLException exc) {
            LOGGER.error("User not inserted!", exc);
        }
    }

    /**
     * Executes request into the database (table 'student_courses') to insert
     * the current user and course. In other words, the current user subscribes
     * to the current course
     *
     * @param user Current user
     */
    public void deleteUserCourse(final User user) {
        try (Connection con = this.context.getDataSource().getConnection();
            DSLContext ctx = DSL.using(con, SQLDialect.MYSQL)
        ) {
            ctx.deleteFrom(StudentCourses.STUDENT_COURSES)
            .where(
                StudentCourses.STUDENT_COURSES.COURSE_ID.eq(this.getId())
                .and(StudentCourses.STUDENT_COURSES.STUD_ID.eq(user.getId()))
            )
            .execute();
        } catch (final SQLException exc) {
            LOGGER.error("User not deleted!", exc);
        }
    }

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter.
     * @param id ID
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Getter.
     * @return Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter.
     * @param name Name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter.
     * @return Description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter.
     * @param description Description
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
