package com.devproserv.courses.model;

import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.SEL_USER;

public class PrelUser extends User {

    public enum Role {
        STUD, LECT, ADMIN
    }

    private static final Logger logger = LogManager.getLogger(PrelUser.class.getName());

    private final AppContext appContext;
    private final String login;
    private final String password;

    public PrelUser(AppContext appContext, String login, String password) {
        this.appContext = appContext;
        this.login = login;
        this.password = password;
    }

    /**
     * Checks if the user with specified login and password exists in the database.
     * The method is used during login procedure
     *
     * @return {@code true} if the user exists
     */
    @Override
    public boolean exists() {
        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(SEL_USER)
        ) {
            prepStmt.setString(1, login);
            prepStmt.setString(2, password);
            ResultSet result = prepStmt.executeQuery();
            // return true if the result query contains at least one row
            return result.next();
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
        return false;
    }

    @Override
    public void loadFields() {
        // TODO
    }

    @Override
    public void prepareJspData(HttpServletRequest request) {
        // TODO
    }

    public String path(HttpServletRequest request) {
        /* checks if the user with entered credentials exists in the database */
        if (!exists()) {
            logger.info("Login " + login + " does not exist.");

            String validResponse = "Wrong username or password! Try again!";
            request.setAttribute("message", validResponse);
            return LOGIN_PAGE;
        }

        User student = convertToTrue();

        /* gets the link to the current session or creates new one and attaches the user to the session */
        HttpSession session = request.getSession(); // TODO add login.jsp filter to check validated session
        session.setAttribute(session.getId(), student);

        student.prepareJspData(request);

        return student.getPath();
    }


    /**
     * TODO
     * Creates new instance of  with given login and password.
     * Login and password should match to ones in the database
     * (the method should be called after {@code userExists} method.
     *
     *
     * @return new instance of Student, Lecturer or Administrator
     */
    @Override
    public User convertToTrue() {
        Role role = getUserRole(login, password);
        User user = new EmptyUser();
        switch (role) {
            case STUD:
                user = new Student(appContext);
                break;
            case LECT:
                user = new Lecturer(appContext);
                break;
            case ADMIN:
                user = new Admin(appContext);
                break;
        }

        user.setLogin(login);
        user.setPassword(password);
        user.loadFields();
        return user;
    }

    /**
     * Executes query to database to define user role in the system.
     *
     * @param login login
     * @param password password
     * @return {@code true} if the user exists
     */
    private Role getUserRole(String login, String password) {
        Role role = Role.STUD;
        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(SEL_USER)
        ) {
            prepStmt.setString(1, login);
            prepStmt.setString(2, password);
            ResultSet result = prepStmt.executeQuery();
            if (result.next()) {
                role = Role.valueOf(result.getString(6));
            }
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
        return role;
    }


}
