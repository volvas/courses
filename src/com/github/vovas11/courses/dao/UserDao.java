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
    final static String		      SELECT_SQL = "SELECT * FROM students WHERE login=? AND password=?";
    final static String		 SELECT_NAME_SQL = "SELECT * FROM students WHERE login=?";
    final static String		 INSERT_USER_SQL = "INSERT INTO students"
						 + "(firstname, lastname, login, password, department)"
						 + " VALUES(?, ?, ?, ?, ?)";
    final static String	    SELECT_ALL_USERS_SQL = "SELECT * FROM students";
    final static String      GET_USER_FIELDS_SQL = "SELECT student_id, firstname, lastname, department"
						 + " FROM students WHERE login = ?";
    
    public UserDao(DataSource datasrc) {
	this.datasrc = datasrc;
    }
    
    public boolean isExist(User user) {
	Connection conn = null;
	// TODO check for null
	try {
	    conn = datasrc.getConnection();
	    PreparedStatement prepStmt = conn.prepareStatement(SELECT_SQL);
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
	    PreparedStatement prepStmt = conn.prepareStatement(SELECT_NAME_SQL);
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
	    PreparedStatement prepStmt = conn.prepareStatement(INSERT_USER_SQL);
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
	    ResultSet result = stmt.executeQuery(SELECT_ALL_USERS_SQL);
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
    
    /**
     * Adds rest of the fields into the object.
     * @param   user   the current user
     */
    public void getFieldsForUser(User user) {
	Connection connection = null;
	try {
	    connection = datasrc.getConnection();
	    
	    PreparedStatement prepStmt = connection.prepareStatement(GET_USER_FIELDS_SQL);
	    prepStmt.setString(1, user.getLogin());
	    ResultSet result = prepStmt.executeQuery();
	    while (result.next()) {
		user.setId(result.getInt(1));
		user.setFirstName(result.getString(2));
		user.setLastName(result.getString(3));
		user.setDepartment(result.getString(4));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }
}
