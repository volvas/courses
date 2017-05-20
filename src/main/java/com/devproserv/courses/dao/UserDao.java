package com.devproserv.courses.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import com.devproserv.courses.dao.User.Role;

/**
 * Provides CRUD methods for communication between application and database.
 * Implements operations with user tables (users and students).
 * 
 * @author vovas11
 * @see User
 * @see DaoFactory
 */
public class UserDao {
    /* link to the connection (interface) to the database */
    DataSource datasrc;
    
    /* Predefined SQL statements that are used for execution requests in the database */
    final static String      SELECT_USER_SQL = "SELECT * FROM users WHERE login=? AND password=?;";
    final static String     SELECT_LOGIN_SQL = "SELECT * FROM users WHERE login=?;";
    final static String      INSERT_USER_SQL = "INSERT INTO users "
                        + "(firstname, lastname, login, password, role) "
                        + "VALUES(?, ?, ?, ?, ?);";
    final static String   INSERT_STUDENT_SQL = "INSERT INTO students "
                        + "(stud_id, faculty) "
                        + "VALUES(?, ?);";
    final static String  GET_USER_FIELDS_SQL = "SELECT user_id, firstname, lastname, faculty FROM users "
                        + "JOIN students ON users.user_id = students.stud_id WHERE login = ?;";
    
    public UserDao(DataSource datasrc) {
        this.datasrc = datasrc;
    }
    
    /**
     * Checks if the user with specified login and password exists in the database.
     * 
     * @param   user   the current user
     * @return {@code true} if the user exists
     */
    public boolean userExists(User user) {
        /* gets connection from Connection pool and prepares SQL statement */
        try (
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(SELECT_USER_SQL)
        ) {
            prepStmt.setString(1, user.getLogin());
            prepStmt.setString(2, user.getPassword());
            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();
            /* returns true if the result query contains at least one row */
            return result.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Executes query to database to define user role in the system.
     * 
     * @param   user the current user
     * @return {@code true} if the user exists
     */
    public void getUserRole(User user) {
        /* gets connection from Connection pool and prepares SQL statement */
        try (
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(SELECT_USER_SQL)
        ) {
            prepStmt.setString(1, user.getLogin());
            prepStmt.setString(2, user.getPassword());

            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();

            if (result.next()) {
                user.setRole(Role.valueOf(result.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Checks if the user with specified login exists in the database.
     * 
     * @param   user   the current user
     * @return {@code true} if the user exists and {@code false} if does not
     */
    public boolean loginExists(User user) {
        /* gets connection from Connection pool and prepares SQL statement */
        try (
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(SELECT_LOGIN_SQL)
        ) {
            prepStmt.setString(1, user.getLogin());

            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();

            /* returns true if result is not empty */
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // less changes in the database if something is wrong
    }
    
    /**
     * Executes request into the database (tables 'users' and 'students') to insert the current user.
     * 
     * @param   user   the current user
     */
    public boolean insertStudent(Student user) {
        /* gets connection from Connection pool and prepares SQL statement */
        try (
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmtOne = con.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement prepStmtTwo = con.prepareStatement(INSERT_STUDENT_SQL)
        ) {
            /* inserts data into the table 'users' */
            prepStmtOne.setString(1, user.getFirstName());
            prepStmtOne.setString(2, user.getLastName());
            prepStmtOne.setString(3, user.getLogin());
            prepStmtOne.setString(4, user.getPassword());
            prepStmtOne.setString(5, user.getRole().toString());
            /* returns false if inserting fails */
            if (prepStmtOne.executeUpdate() == 0) return false;
            /* returns autogenerated ID and assigns it to the user instance */
            ResultSet generatedKey = prepStmtOne.getGeneratedKeys();
            if (generatedKey.next()) {
                user.setId(generatedKey.getInt(1));
            }
            /* inserts data into the table 'students' */
            prepStmtTwo.setInt(1, user.getId());
            prepStmtTwo.setString(2, user.getFaculty());
            if (prepStmtTwo.executeUpdate() == 0) return false;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Adds rest of the fields into the object.
     * @param   student   the current user
     */
    public void appendRestUserFields(Student student) {
        /* gets connection from Connection pool and prepares SQL statement */
        try (
            Connection con = datasrc.getConnection();
            PreparedStatement prepStmt = con.prepareStatement(GET_USER_FIELDS_SQL)
        ) {
            prepStmt.setString(1, student.getLogin());

            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();

            /* fills in the instance's fields */
            while (result.next()) {
                student.setId(result.getInt(1));
                student.setFirstName(result.getString(2));
                student.setLastName(result.getString(3));
                student.setFaculty(result.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}