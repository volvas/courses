package com.github.vovas11.courses.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    final static String  SELECT_SUBSCR_COURSES_SQL = "SELECT * FROM courses WHERE courses.course_id IN ("
    						   + "SELECT student_courses.course_id "
    						   + "FROM student_courses, students "
    						   + "WHERE student_courses.student_id = students.student_id "
    						   + "AND students.login = ?)";
    
    final static String   SELECT_AVAIL_COURSES_SQL = "SELECT * FROM courses WHERE course_id NOT IN ("
                                		   + "SELECT course_id FROM student_courses "
                                		   + "WHERE student_id IN ("
                                		   + "SELECT student_id FROM students "
                                		   + "WHERE login = ?))";
    
    final static String    INSERT_USER_COURSES_SQL = "INSERT INTO student_courses "
						   + "(course_id, student_id, state) "
						   + "VALUES(?, (SELECT students.student_id "
						   + "FROM students WHERE login = ?), 'STARTED')";
    
    public CourseDao(DataSource datasrc) {
	this.datasrc = datasrc;
    }
    
    /**
     * Executes request into the database and returns list of courses that are available
     * for the current student to subscribe.
     * 
     * @param   user   the current user
     * @return list of available courses from the database
     */
    public List<Course> getAvailableCourses(User user) {
	
	/* list of the available courses to be returned */
	List<Course> availCourses = new ArrayList<Course>();
	
	/* link to the current database */
	Connection conn = null;
	
	try {
	    /* gets connection to the database from Connection pool */
	    conn = datasrc.getConnection();
	    
	    /* prepares SQL statement with parameter */
	    PreparedStatement prepStmt = conn.prepareStatement(SELECT_AVAIL_COURSES_SQL);
	    prepStmt.setString(1, user.getLogin());
	    
	    /* executes the query and receives the result table wrapped by ResultSet */
	    ResultSet result = prepStmt.executeQuery();
	    
	    /* runs through all rows of the result table, creates an instance of the Course,
	     * fills in the instance's fields, and put it into result list */
	    while (result.next()) {
		Course course = new Course();
		course.setId(result.getInt(1));
		course.setName(result.getString(2));
		course.setDescription(result.getString(3));
		availCourses.add(course);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return availCourses;
    }
    
    /**
     * Executes request into the database for getting the courses
     * that a student has been subscribed to
     * 
     * @param   user   the current user
     * @return list of subscribed courses for the user
     */
    public List<Course> getSubscribedCourses(User user) {
	
	/* list of the subscribed courses to be returned */
	List<Course> subscrCourses = new ArrayList<Course>();
	
	/* link to the current database */
	Connection conn = null;
	
	try {
	    /* gets connection to the database from Connection pool */
	    conn = datasrc.getConnection();
	    
	    /* prepares SQL statement with parameter */
	    PreparedStatement prepStmt = conn.prepareStatement(SELECT_SUBSCR_COURSES_SQL);
	    prepStmt.setString(1, user.getLogin());
	    
	    /* executes the query and receives the result table */
	    ResultSet result = prepStmt.executeQuery();
	    
	    /* runs through all rows of the result table, creates an instance of the Course,
	     * fills in the instance's fields, and put it into result list */
	    while (result.next()) {
		Course course = new Course();
		course.setId(result.getInt(1));
		course.setName(result.getString(2));
		course.setDescription(result.getString(3));
		subscrCourses.add(course);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return subscrCourses;
    }
    
    /**
     * Executes request into the database (table 'student_courses') to insert the current user
     * and course. In other words, the current user subscribes to the current course
     * 
     * @param   user   the current user
     * @param   course   the current course
     */
    public void insertUserCourse(User user, Course course) {
	
	/* link to the current database */
	Connection conn = null;
	
	try {
	    /* gets connection to the database from Connection pool */
	    conn = datasrc.getConnection();
	    
	    /* prepares SQL statement with parameters ans execute the query*/
	    PreparedStatement prepStmt = conn.prepareStatement(INSERT_USER_COURSES_SQL);
	    prepStmt.setInt(1, course.getId());
	    prepStmt.setString(2, user.getLogin());
	    prepStmt.execute();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }
}
