package com.devproserv.courses.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.devproserv.courses.model.User;
import com.devproserv.courses.model.Course;

import static com.devproserv.courses.config.MainConfig.SELECT_SUBSCR_COURSES_SQL;
import static com.devproserv.courses.config.MainConfig.SELECT_AVAIL_COURSES_SQL;
import static com.devproserv.courses.config.MainConfig.INSERT_USER_COURSES_SQL;
import static com.devproserv.courses.config.MainConfig.DELETE_USER_COURSES_SQL;

/**
 * Provides methods for communication between application and the database and manipulates
 * with table 'courses'. Part of DAO design pattern.
 * 
 * @author vovas11
 * @see Course
 * @see DaoFactory
 */
public class CourseDao {

    Connection connection;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public CourseDao(Connection connection) {
        this.connection = connection;
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
            /* prepares SQL statement with parameter */
            PreparedStatement prepStmt = connection.prepareStatement(SELECT_AVAIL_COURSES_SQL);
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
            logger.log(Level.SEVERE, "Request to database failed", e);
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
            /* prepares SQL statement with parameter */
            PreparedStatement prepStmt = connection.prepareStatement(SELECT_SUBSCR_COURSES_SQL);
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
            logger.log(Level.SEVERE, "Request to database failed", e);
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
            PreparedStatement prepStmt = connection.prepareStatement(INSERT_USER_COURSES_SQL);
        ) {
            prepStmt.setInt(1, course.getId());
            prepStmt.setString(2, user.getLogin());
            prepStmt.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Request to database failed", e);
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
            /* prepares SQL statement with parameter */
            PreparedStatement prepStmt = connection.prepareStatement(DELETE_USER_COURSES_SQL);
        ) {;
            prepStmt.setInt(1, course.getId());
            prepStmt.setInt(2, user.getId());
            prepStmt.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Request to database failed", e);
        }
    }
}