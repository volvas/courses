package com.devproserv.courses.model;

import com.devproserv.courses.servlet.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.devproserv.courses.config.MainConfig.GET_USER_FIELDS_SQL;
import static com.devproserv.courses.config.MainConfig.INSERT_STUDENT_SQL;
import static com.devproserv.courses.config.MainConfig.INSERT_USER_SQL;
import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.SELECT_AVAIL_COURSES_SQL;
import static com.devproserv.courses.config.MainConfig.SELECT_LOGIN_SQL;
import static com.devproserv.courses.config.MainConfig.SELECT_SUBSCR_COURSES_SQL;
import static com.devproserv.courses.config.MainConfig.SIGNUP_PAGE;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;

public class Student extends User {

    private static final Logger logger = LogManager.getLogger(Student.class.getName());

    private final AppContext appContext;

    private String faculty;


    public Student(AppContext appContext) {
        this.appContext = appContext;
    }


    @Override
    public boolean exists() {
        return true; // TODO
    }

    /**
     * Adds rest of the fields into the object.
     *
     */
    @Override
    public void loadFields() {
        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(GET_USER_FIELDS_SQL)
        ) {
            prepStmt.setString(1, getLogin());
            ResultSet result = prepStmt.executeQuery();
            while (result.next()) {
                setId(result.getInt(1));
                setFirstName(result.getString(2));
                setLastName(result.getString(3));
                setFaculty(result.getString(4));
            }
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
        setPath(STUDENT_PAGE);
    }

    public String path(HttpServletRequest request) {
        /* checks if login exists and if yes returns back to the sign up
         * page, if no inserts new user into database and proceeds to the login page*/
        if (loginExists()) {
            request.setAttribute("message", "User already exists!");
            return SIGNUP_PAGE;
        } else if (createUser()) {
            return LOGIN_PAGE;
        } else {
            request.setAttribute("message", "User has not been created. Try again.");
            return SIGNUP_PAGE;
        }
    }

    /**
     * Prepares data to be displayed on the own student's page and attaches the data
     * to HTTP request. Then JSP servlet will handle this request.
     *
     * @param request HTTP request
     */
    @Override
    public void prepareJspData(HttpServletRequest request) {

        request.setAttribute("student", this);

        /* gets subscribed courses and available courses */
        List<Course> subscribedCourses = getSubscribedCourses();
        request.setAttribute("subscrcourses", subscribedCourses);
        List<Course> availableCourses = getAvailableCourses();
        request.setAttribute("courses", availableCourses);
    }

    /**
     * Executes request into the database for getting the courses that a student
     * has been subscribed to
     *
     * @return list of subscribed courses for the user
     */
    private List<Course> getSubscribedCourses() {
        /* list of the subscribed courses to be returned */
        List<Course> subscrCourses = new ArrayList<>();

        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(SELECT_SUBSCR_COURSES_SQL)
        ) {
            prepStmt.setString(1, getLogin());

            /* executes the query and receives the result table */
            ResultSet result = prepStmt.executeQuery();

            /* runs through all rows of the result table, creates an instance of
             * the Course, fills in the instance's fields, and put it into
             * result list */
            while (result.next()) {
                Course course = new Course(appContext);
                course.setId(result.getInt(1));
                course.setName(result.getString(2));
                course.setDescription(result.getString(3));
                subscrCourses.add(course);
            }
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
        return subscrCourses;
    }

    /**
     * Executes request into the database and returns list of courses that are
     * available for the current student to subscribe.
     *
     * @return list of available courses from the database
     */
    private List<Course> getAvailableCourses() {
        /* list of the available courses to be returned */
        List<Course> availCourses = new ArrayList<>();

        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(SELECT_AVAIL_COURSES_SQL)
        ) {
            prepStmt.setString(1, getLogin());

            /* executes the query and receives the result table wrapped by ResultSet */
            ResultSet result = prepStmt.executeQuery();

            /* runs through all rows of the result table, creates an instance of
             * the Course, fills in the instance's fields, and put it into
             * result list */
            while (result.next()) {
                Course course = new Course(appContext);
                course.setId(result.getInt(1));
                course.setName(result.getString(2));
                course.setDescription(result.getString(3));
                availCourses.add(course);
            }
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
        return availCourses;
    }


    // signup
    /**
     * Checks if the specified login exists in the database. The method is used during sign up procedure.
     *
     * @return {@code true} if the user exists and {@code false} if does not
     */
    private boolean loginExists() {
        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(SELECT_LOGIN_SQL)
        ) {
            prepStmt.setString(1, getLogin());
            ResultSet result = prepStmt.executeQuery();
            /* returns true if result is not empty */
            return result.next();
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
        return true; // less changes in the database if something is wrong
    }

    /**
     * Creates new instance of {@link Student} with given parameters,
     * checks if the user with specified login exists in the database, and if no
     * inserts the user into the database (tables 'users' and 'students')
     *
     *
     * @return {@code true} if the user has been created successfully and {@code false} if is not
     */
    private boolean createUser() {
        setLogin(getLogin());
        setPassword(getPassword());
        setFirstName(getFirstName());
        setLastName(getLastName());
        setFaculty(getFaculty());

        return insertUser();
    }

    /**
     * Executes request into the database (tables 'users' and 'students') to insert the current user.
     *
     * @return {@code true} if the user has been created successfully and {@code false} if is not
     */
    private boolean insertUser() {

        try (Connection connection = appContext.getDataSource().getConnection();
             PreparedStatement prepStmtOne = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement prepStmtTwo = connection.prepareStatement(INSERT_STUDENT_SQL)
        ) {
            /* inserts data into the table 'users' */
            prepStmtOne.setString(1, getFirstName());
            prepStmtOne.setString(2, getLastName());
            prepStmtOne.setString(3, getLogin());
            prepStmtOne.setString(4, getPassword());
            prepStmtOne.setString(5, PrelUser.Role.STUD.toString());
            /* returns false if inserting fails */
            if (prepStmtOne.executeUpdate() == 0) return false;
            /* returns autogenerated ID and assigns it to the user instance */
            ResultSet generatedKey = prepStmtOne.getGeneratedKeys();
            if (generatedKey.next()) {
                setId(generatedKey.getInt(1));
            }
            /* inserts data into the table 'students' */
            prepStmtTwo.setInt(1, getId());
            prepStmtTwo.setString(2, getFaculty());
            return prepStmtTwo.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Request to database failed", e);
        }
        return false;
    }



    private String getFaculty() {
        return faculty;
    }
    private void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
