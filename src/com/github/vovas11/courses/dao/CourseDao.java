package com.github.vovas11.courses.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class CourseDao {
    DataSource datasrc;
    final static String selectAll = "SELECT * FROM courses";
    
    public CourseDao(DataSource datasrc) {
	this.datasrc = datasrc;
    }
    public List<Course> getAllCourses() {
	List<Course> allCourses = new ArrayList<Course>();
	Connection conn = null;
	try {
	    conn = datasrc.getConnection();
	    Statement stmt = conn.createStatement();
	    ResultSet result = stmt.executeQuery(selectAll);
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
}
