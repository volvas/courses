package com.devproserv.courses.model;

import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.devproserv.courses.config.MainConfig.DELETE_USER_COURSES_SQL;
import static com.devproserv.courses.config.MainConfig.INSERT_USER_COURSES_SQL;

/**
 * Represents the entity of the Course. Maps the table 'courses' in the database. 
 * Part of DAO design pattern.
 * 
 * @author vovas11
 */
public class Course {

    private static final Logger logger = LogManager.getLogger(Course.class.getName());

    private final AppContext appContext;

    private int id;
    private String name;
    private String description;


    public Course(AppContext appContext) {
        this.appContext = appContext;
    }

    /**
     * Executes request into the database (table 'student_courses') to insert
     * the current user and course. In other words, the current user subscribes
     * to the current course
     *
     * @param user the current user
     */
    public void insertUserCourse(User user) {
        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(INSERT_USER_COURSES_SQL)
        ) {
            prepStmt.setInt(1, getId());
            prepStmt.setString(2, user.getLogin());
            prepStmt.execute();
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
    }

    /**
     * Executes request into the database (table 'student_courses') to insert
     * the current user and course. In other words, the current user subscribes
     * to the current course
     *
     * @param user the current user
     */
    public void deleteUserCourse(User user) {
        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(DELETE_USER_COURSES_SQL)
        ) {
            prepStmt.setInt(1, getId());
            prepStmt.setInt(2, user.getId());
            prepStmt.execute();
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
    }

    
    /* getters and setters for the private fields */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
