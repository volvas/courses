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

import com.devproserv.courses.form.EnrollForm;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a student entity.
 *
 * @since 0.5.0
 */
public final class Student implements Responsible {
    /**
     * Serial number.
     */
    private static final long serialVersionUID = 1561218027053448066L;

    /**
     * Course persistence instance.
     */
    private transient NestCourses courses;

    /**
     * User.
     */
    private final FullNameUser user;

    /**
     * Faculty field.
     */
    private final String faculty;

    /**
     * Constructor.
     *
     * @param user Full named User
     * @param faculty Faculty
     */
    public Student(final FullNameUser user, final String faculty) {
        this(new NestCourses(new Db()), user, faculty);
    }

    /**
     * Primary constructor.
     *
     * @param courses Courses
     * @param user Full named User
     * @param faculty Faculty
     */
    public Student(final NestCourses courses, final FullNameUser user, final String faculty) {
        this.courses = courses;
        this.user    = user;
        this.faculty = faculty;
    }

    @Override
    public Response response() {
        final List<Course> all = this.courses.allCourses();
        final List<Integer> enrolledids = this.courses.enrolledCourseIds(this.getId());
        final List<Course> enrolled = all.stream()
            .filter(course -> enrolledids.contains(course.getId()))
            .collect(Collectors.toList());
        final List<Course> available = all.stream()
            .filter(course -> !enrolledids.contains(course.getId()))
            .collect(Collectors.toList());
        final Map<String, Object> payload = new HashMap<>();
        payload.put("student", this);
        payload.put("subscrcourses", enrolled);
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
        this.courses = new NestCourses(new Db());
    }
}
