package com.devproserv.courses.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.devproserv.courses.model.Student;
import com.devproserv.courses.model.User;
import com.devproserv.courses.model.User.Role;

import static com.devproserv.courses.config.MainConfig.SELECT_USER_SQL;
import static com.devproserv.courses.config.MainConfig.SELECT_LOGIN_SQL;
import static com.devproserv.courses.config.MainConfig.INSERT_USER_SQL;
import static com.devproserv.courses.config.MainConfig.INSERT_STUDENT_SQL;
import static com.devproserv.courses.config.MainConfig.GET_USER_FIELDS_SQL;

/**
 * Provides CRUD methods for communication between application and database.
 * Implements operations with user tables (users and students).
 * 
 * @author vovas11
 * @see User
 */
public class UserDao {
    /** link to the connection to the database */
    Connection connection;
    
    public UserDao(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Creates new instance of {@link Student} with given parameters.
     * Is used during login procedure (stage 1)
     * 
     * @param login argument representing login
     * @param password argument representing password
     * 
     * @return new instance of {@link Student}
     */
    public User getUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
    
    /**
     * Creates new instance of {@link Student} with given parameters.
     * Is used during login procedure (stage 2)
     * 
     * @param login argument representing login
     * @param password argument representing password
     * 
     * @return new instance of {@link Student}
     */
    public User getUser(String login, String password, Role role) {
        Student student = new Student();
        student.setLogin(login);
        student.setPassword(password);
        student.setRole(role);
        return student;
    }
    
    /**
     * Creates new instance of {@link Student} with given parameters,
     * checks if the user with specified login exists in the database, and if no
     * inserts the user into the database (tables 'users' and 'students')
     * 
     * @param login argument representing login
     * @param password argument representing password
     * @param firstName argument representing first name
     * @param lastName argument representing last name
     * @param faculty argument representing faculty
     * 
     * @return {@code true} if the user has been created successfully and {@code false} if is not
     */
    public boolean createUser(String login, String password, String firstName, String lastName, String faculty) {
        Student student = new Student();
        student.setLogin(login);
        student.setPassword(password);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setStudentRole();
        student.setFaculty(faculty);
        
        return insertUser(student) ? true : false;
    }

    /**
     * Checks if the specified login exists in the database.
     * 
     * @param   String   login (user name)
     * @return {@code true} if the user exists and {@code false} if does not
     */
    public boolean loginExists(String login) {
        /* prepares SQL statement */
        try (PreparedStatement prepStmt = connection.prepareStatement(SELECT_LOGIN_SQL)) {
            prepStmt.setString(1, login);
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
     * @return {@code true} if the user has been created successfully and {@code false} if is not
     */
    private boolean insertUser(User user) {
        Student student;
        if (user instanceof Student) {
            student = (Student) user;
            /* prepares SQL statement */
            try (
                PreparedStatement prepStmtOne = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement prepStmtTwo = connection.prepareStatement(INSERT_STUDENT_SQL)
            ) {
                /* inserts data into the table 'users' */
                prepStmtOne.setString(1, student.getFirstName());
                prepStmtOne.setString(2, student.getLastName());
                prepStmtOne.setString(3, student.getLogin());
                prepStmtOne.setString(4, student.getPassword());
                prepStmtOne.setString(5, student.getRole().toString());
                /* returns false if inserting fails */
                if (prepStmtOne.executeUpdate() == 0) return false;
                /* returns autogenerated ID and assigns it to the user instance */
                ResultSet generatedKey = prepStmtOne.getGeneratedKeys();
                if (generatedKey.next()) {
                    student.setId(generatedKey.getInt(1));
                }
                /* inserts data into the table 'students' */
                prepStmtTwo.setInt(1, student.getId());
                prepStmtTwo.setString(2, student.getFaculty());
                if (prepStmtTwo.executeUpdate() == 0) return false;
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /**
     * Checks if the user with specified login and password exists in the database.
     * 
     * @param   user   the current user
     * @return {@code true} if the user exists
     */
    public boolean userExists(User user) {
        /* prepares SQL statement */
        try (
            PreparedStatement prepStmt = connection.prepareStatement(SELECT_USER_SQL)
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
        /* prepares SQL statement */
        try (
            PreparedStatement prepStmt = connection.prepareStatement(SELECT_USER_SQL)
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
     * Adds rest of the fields into the object.
     * @param   student   the current user
     */
    public void appendRestUserFields(Student student) {
        /* prepares SQL statement */
        try (
            PreparedStatement prepStmt = connection.prepareStatement(GET_USER_FIELDS_SQL)
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