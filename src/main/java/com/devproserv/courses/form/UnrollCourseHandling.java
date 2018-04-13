package com.devproserv.courses.form;

import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.Student;

public class UnrollCourseHandling extends CourseHandling {
    @Override
    String courseIdParameter() {
        return "courseunsubscrid";
    }

    @Override
    String errorMessageParameter() {
        return "messageuns";
    }

    @Override
    void changeEntry(Course course, Student user) {
        course.deleteUserCourse(user);
    }
}
