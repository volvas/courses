package com.github.vovas11.courses.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class CourseDao {
    DataSource datasrc;
    // TODO pull out all constants into separate class(?)
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
     * Method getAllCourses
     * 
     * @param   user   the current user
     * @return all courses in the database
     */
    public List<Course> getAvailableCourses(User user) {
	List<Course> availCourses = new ArrayList<Course>();
	Connection conn = null;
	try {
	    // gets connection to database from Connection pool
	    conn = datasrc.getConnection();
	    PreparedStatement prepStmt = conn.prepareStatement(SELECT_AVAIL_COURSES_SQL);
	    prepStmt.setString(1, user.getLogin());
	    ResultSet result = prepStmt.executeQuery();
	    
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
     * getSubscribedCourses uses query into database for getting the courses
     * that a student has been subscribed to
     * 
     * @param   user   the current user
     * @return subscribed courses for the user
     */
    public List<Course> getSubscribedCourses(User user) {
	List<Course> subscrCourses = new ArrayList<Course>();
	Connection conn = null;
	try {
	    // gets connection to database from Connection pool
	    conn = datasrc.getConnection();
	    PreparedStatement prepStmt = conn.prepareStatement(SELECT_SUBSCR_COURSES_SQL);
	    prepStmt.setString(1, user.getLogin());
	    ResultSet result = prepStmt.executeQuery();
	    
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
     * insertUserCourse uses query into database for insert the user and course
     * in other words, the current user subscribes to the current course
     * 
     * @param   user   the current user
     * @param   course   the current course
     * @return nothing TODO to return false/true if the table in DB didn't changed/changed
     */
    public void insertUserCourse(User user, Course course) {
	
	Connection conn = null;
	
	try {
	    conn = datasrc.getConnection();
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
