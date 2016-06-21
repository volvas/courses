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
    final static String selectAll = "SELECT * FROM courses";
    
    public CourseDao(DataSource datasrc) {
	this.datasrc = datasrc;
    }
    
    /**
     * Method getAllCourses
     * 
     * 
     * @return all courses in the database
     */
    public List<Course> getAllCourses(User user) {
	List<Course> allCourses = new ArrayList<Course>();
	Connection conn = null;
	try {
	    // gets connection to database from Connection pool
	    conn = datasrc.getConnection();
	    PreparedStatement prepStmt = conn.prepareStatement(selectAll);
	    
	    ResultSet result = prepStmt.executeQuery();
	    while (result.next()) {
		Course course = new Course();
		course.setId(result.getInt(1));
		course.setName(result.getString(2));
		course.setDescription(result.getString(3));
		allCourses.add(course);
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
	return allCourses;
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
	    System.out.println(subscrCourses);
	    
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
