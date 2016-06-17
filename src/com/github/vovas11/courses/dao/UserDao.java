/**
 * 
 * 
 * 
 */
package com.github.vovas11.courses.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class UserDao {
    DataSource datasrc;
    final static String selectSql = "SELECT * FROM students WHERE login=? AND password=?";
    final static String selectNameSql = "SELECT * FROM students WHERE login=?";
    final static String insertSql = "INSERT INTO students"
	    			    + "(firstname, lastname, login, password, department)"
	    			    + " VALUES(?, ?, ?, ?, ?)";
    final static String selectAll = "SELECT * FROM students";
    
    public UserDao(DataSource datasrc) {
	this.datasrc = datasrc;
    }
    
    public boolean isExist(User user) {
	Connection conn = null;
	// TODO check for null
	try {
	    conn = datasrc.getConnection();
	    PreparedStatement prepStmt = conn.prepareStatement(selectSql);
	    prepStmt.setString(1, user.getLogin());
	    prepStmt.setString(2, user.getPassword());
	    ResultSet result = prepStmt.executeQuery();
	    return result.next();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return false;
    }
    public boolean isLoginExist(User user) {
	Connection conn = null;
	// FIXME check for null
	try {
	    conn = datasrc.getConnection();
	    PreparedStatement prepStmt = conn.prepareStatement(selectNameSql);
	    prepStmt.setString(1, user.getLogin());
	    ResultSet result = prepStmt.executeQuery();
	    return result.next();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return false;
    }
    public void insert(User user) {
	Connection conn = null;
	try {
	    conn = datasrc.getConnection();
	    PreparedStatement prepStmt = conn.prepareStatement(insertSql);
	    prepStmt.setString(1, user.getFirstName());
	    prepStmt.setString(2, user.getLastName());
	    prepStmt.setString(3, user.getLogin());
	    prepStmt.setString(4, user.getPassword());
	    prepStmt.setString(5, user.getDepartment());
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
    public List<User> getAllUsers() {
	List<User> allUsers = new ArrayList<User>();
	Connection conn = null;
	try {
	    conn = datasrc.getConnection();
	    Statement stmt = conn.createStatement();
	    ResultSet result = stmt.executeQuery(selectAll);
	    while (result.next()) {
		User user = new User();
		user.setId(result.getInt(1));
		user.setLogin(result.getString(4));
		user.setPassword(result.getString(5));
		user.setFirstName(result.getString(2));
		user.setLastName(result.getString(3));
		user.setDepartment(result.getString(6));
		allUsers.add(user);
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
	return allUsers;
    }
}
