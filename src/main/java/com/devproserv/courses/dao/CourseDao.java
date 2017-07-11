package com.devproserv.courses.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.devproserv.courses.model.User;
import com.devproserv.courses.model.Course;

import javax.sql.DataSource;

/**
 * Provides methods for communication between application and the database and manipulates
 * with table 'courses'. Part of DAO design pattern.
 * 
 * @author vovas11
 * @see Course
 * @see DaoFactory
 */
public class CourseDao {
    
    /* link to the connection (interface) to the database */
    DataSource datasrc;
    
    /* Predefined SQL statements that are used for execution requests in the database, table 'courses' */
    final static String SELECT_SUBSCR_COURSES_SQL = "SELECT * FROM courses WHERE courses.course_id IN ("
            + "SELECT student_courses.course_id FROM student_courses, users "
            + "WHERE student_courses.stud_id = users.user_id AND users.login = ?);";
    
    final static String SELECT_AVAIL_COURSES_SQL = "SELECT * FROM courses WHERE course_id NOT IN ("
            + "SELECT course_id FROM student_courses WHERE stud_id IN ("
            + "SELECT user_id FROM users WHERE login = ?));";
    
    final static String INSERT_USER_COURSES_SQL = "INSERT INTO student_courses "
            + "(course_id, student_id, state) VALUES(?, (SELECT students.student_id "
            + "FROM students WHERE login = ?), 'STARTED');";
    
    final static String DELETE_USER_COURSES_SQL = "DELETE FROM student_courses WHERE course_id = ? AND student_id = ?;";
    
    public CourseDao(DataSource datasrc) {
        this.datasrc = datasrc;
    }

    /**
     * Executes request into the database and returns list of courses that are
     * available for the current student to subscribe.
     * 
     * @param user the current user
     * @return list of available courses from the database
     */
    public List<Course> getAvailableCourses(User user) {
        /* list of the available courses to be returned */
        List<Course> availCourses = new ArrayList<Course>();

        try (
            /* gets connection to the database from Connection pool */
            /* prepares SQL statement with parameter */
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(SELECT_AVAIL_COURSES_SQL);
        ) {
            prepStmt.setString(1, user.getLogin());
            
            /* executes the query and receives the result table wrapped by ResultSet */
            ResultSet result = prepStmt.executeQuery();

            /* runs through all rows of the result table, creates an instance of
             * the Course, fills in the instance's fields, and put it into
             * result list */
            while (result.next()) {
                Course course = new Course();
                course.setId(result.getInt(1));
                course.setName(result.getString(2));
                course.setDescription(result.getString(3));
                availCourses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availCourses;
    }

    /**
     * Executes request into the database for getting the courses that a student
     * has been subscribed to
     * 
     * @param user the current user
     * @return list of subscribed courses for the user
     */
    public List<Course> getSubscribedCourses(User user) {
        /* list of the subscribed courses to be returned */
        List<Course> subscrCourses = new ArrayList<Course>();

        try (
            /* gets connection to the database from Connection pool */
            /* prepares SQL statement with parameter */
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(SELECT_SUBSCR_COURSES_SQL);
        ) {
            prepStmt.setString(1, user.getLogin());

            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();

            /* runs through all rows of the result table, creates an instance of
             * the Course, fills in the instance's fields, and put it into
             * result list */
            while (result.next()) {
                Course course = new Course();
                course.setId(result.getInt(1));
                course.setName(result.getString(2));
                course.setDescription(result.getString(3));
                subscrCourses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscrCourses;
    }

    /**
     * Executes request into the database (table 'student_courses') to insert
     * the current user and course. In other words, the current user subscribes
     * to the current course
     * 
     * @param user the current user
     * @param course the current course
     */
    public void insertUserCourse(User user, Course course) {
        try (
            /* gets connection to the database from Connection pool */
            /* prepares SQL statement with parameter */
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(SELECT_SUBSCR_COURSES_SQL);
        ) {
            prepStmt.setInt(1, course.getId());
            prepStmt.setString(2, user.getLogin());
            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Executes request into the database (table 'student_courses') to insert
     * the current user and course. In other words, the current user subscribes
     * to the current course
     * 
     * @param user the current user
     * @param course the current course
     */
    public void deleteUserCourse(User user, Course course) {
        try (
            /* gets connection to the database from Connection pool */
            /* prepares SQL statement with parameter */
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(SELECT_SUBSCR_COURSES_SQL);
        ) {;
            prepStmt.setInt(1, course.getId());
            prepStmt.setInt(2, user.getId());
            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}