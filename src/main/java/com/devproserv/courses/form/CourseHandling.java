package com.devproserv.courses.form;

import com.devproserv.courses.model.Course;
import com.devproserv.courses.model.Student;

abstract class CourseHandling {
    abstract String courseIdParameter();
    abstract String errorMessageParameter();
    abstract void changeEntry(Course course, Student user);
}
