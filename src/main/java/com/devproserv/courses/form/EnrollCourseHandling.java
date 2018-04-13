package com.devproserv.courses.form;

import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.Student;

public class EnrollCourseHandling extends CourseHandling {
    @Override
    String courseIdParameter() {
        return "coursesubscrid";
    }

    @Override
    String errorMessageParameter() {
        return "messagesub";
    }

    @Override
    void changeEntry(Course course, Student user) {
        course.insertUserCourse(user);
    }
}
