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
    final static String selectSubscrCourses = "SELECT * FROM courses WHERE courses.course_id IN ("
    						+ "SELECT student_courses.course_id "
    						+ "FROM student_courses, students "
    						+ "WHERE student_courses.student_id = students.student_id "
    						+ "AND students.login = ?)";
    final static String selectAvailCourses = "SELECT * FROM courses WHERE course_id NOT IN ("
                                		+ "SELECT course_id FROM student_courses "
                                		+ "WHERE student_id IN ("
                                		+ "SELECT student_id FROM students "
                                		+ "WHERE login = ?))";
    
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
	    PreparedStatement prepStmt = conn.prepareStatement(selectAvailCourses);
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
    
    public List<Course> getSubscribedCourses(User user) {
	List<Course> subscrCourses = new ArrayList<Course>();
	Connection conn = null;
	try {
	    // gets connection to database from Connection pool
	    conn = datasrc.getConnection();
	    PreparedStatement prepStmt = conn.prepareStatement(selectSubscrCourses);
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
    
    
}
