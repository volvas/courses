package com.devproserv.courses.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Provides methods for communication between application and the database and manipulates
 * with table 'students'. Part of DAO design pattern.
 * 
 * @author vovas11
 * @see User
 * @see DaoFactory
 */
public class UserDao {
    
    /* link to the connection (interface) to the database */
    DataSource datasrc;
    
    /* Predefined SQL statements that are used for execution requests in the database, table 'students' */
    final static String      SELECT_USER_SQL = "SELECT * FROM students WHERE login=? AND password=?";
    final static String     SELECT_LOGIN_SQL = "SELECT * FROM students WHERE login=?";
    final static String      INSERT_USER_SQL = "INSERT INTO students"
                         + "(firstname, lastname, login, password, department)"
                         + " VALUES(?, ?, ?, ?, ?)";
    final static String     SELECT_ALL_USERS_SQL = "SELECT * FROM students";
    final static String      GET_USER_FIELDS_SQL = "SELECT student_id, firstname, lastname, department"
                         + " FROM students WHERE login = ?";
    
    public UserDao(DataSource datasrc) {
        this.datasrc = datasrc;
    }
    
    /**
     * Checks if the user with specified login and password exists in the database.
     * 
     * @param   user   the current user
     * @return {@code true} if the user exists and {@code false} if does not
     */
    public boolean isExist(User user) {

        /* link to the current database */
        Connection conn = null;

        try {
            /* gets connection to the database from Connection pool */
            conn = datasrc.getConnection();

            /* prepares SQL statement with parameters */
            PreparedStatement prepStmt = conn.prepareStatement(SELECT_USER_SQL);
            prepStmt.setString(1, user.getLogin());
            prepStmt.setString(2, user.getPassword());

            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();

            /* returns true if result is not empty */
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
    
    /**
     * Checks if the user with specified login exists in the database.
     * 
     * @param   user   the current user
     * @return {@code true} if the user exists and {@code false} if does not
     */
    public boolean isLoginExist(User user) {

        /* link to the current database */
        Connection conn = null;

        try {
            /* gets connection to the database from Connection pool */
            conn = datasrc.getConnection();

            /* prepares SQL statement with parameters */
            PreparedStatement prepStmt = conn.prepareStatement(SELECT_LOGIN_SQL);
            prepStmt.setString(1, user.getLogin());

            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();

            /* returns true if result is not empty */
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
        return true; // less changes in the database if something is wrong
    }
    
    /**
     * Executes request into the database (table 'students') to insert the current user.
     * 
     * @param   user   the current user
     */
    public void insert(Student user) { // TODO User replaced with Student

        /* link to the current database */
        Connection conn = null;

        try {
            /* gets connection to the database from Connection pool */
            conn = datasrc.getConnection();

            /* prepares SQL statement with parameters */
            PreparedStatement prepStmt = conn.prepareStatement(INSERT_USER_SQL);
            prepStmt.setString(1, user.getFirstName());
            prepStmt.setString(2, user.getLastName());
            prepStmt.setString(3, user.getLogin());
            prepStmt.setString(4, user.getPassword());
            prepStmt.setString(5, user.getFaculty());

            /* executes the query without returning anything */
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
    
    /**
     * Executes request into the database and returns list of all users
     * 
     * @return list of all users from the database
     */
    public List<User> getAllUsers() {

        /* list of users to be returned */
        List<User> allUsers = new ArrayList<User>();

        /* link to the current database */
        Connection conn = null;
        try {
            /* gets connection to the database from Connection pool */
            conn = datasrc.getConnection();

            /* creates simple SQL statement */
            Statement stmt = conn.createStatement();

            /* executes the query and receives the result table */
            ResultSet result = stmt.executeQuery(SELECT_ALL_USERS_SQL);

            /* runs through all rows of the result table, creates an instance of the User,
             * fills in the instance's fields, and put it into result list */
            while (result.next()) {
                Student user = new Student(); // TODO User replaced with Student
                user.setId(result.getInt(1));
                user.setLogin(result.getString(4));
                user.setPassword(result.getString(5));
                user.setFirstName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setFaculty(result.getString(6));
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
    public void getFieldsForUser(Student user) { // TODO User replaced with Student

        /* link to the current database */
        Connection connection = null;

        try {
            /* gets connection to the database from Connection pool */
            connection = datasrc.getConnection();

            /* prepares SQL statement with parameters */
            PreparedStatement prepStmt = connection.prepareStatement(GET_USER_FIELDS_SQL);
            prepStmt.setString(1, user.getLogin());

            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();

            /* fills in the instance's fields */
            while (result.next()) {
                user.setId(result.getInt(1));
                user.setFirstName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setFaculty(result.getString(4));
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