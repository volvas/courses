package com.devproserv.courses.model;

import com.devproserv.courses.servlet.AppContext;
import com.devproserv.courses.dao.CourseDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.devproserv.courses.config.MainConfig.GET_USER_FIELDS_SQL;
import static com.devproserv.courses.config.MainConfig.SELECT_AVAIL_COURSES_SQL;
import static com.devproserv.courses.config.MainConfig.SELECT_SUBSCR_COURSES_SQL;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;

public class StudentUser extends TrueUser {

    private static final Logger logger = LogManager.getLogger(StudentUser.class.getName());

    private final AppContext appContext;

    private String faculty;


    public StudentUser(AppContext appContext) {
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

    /**
     * Prepares data to be displayed on the own student's page and attaches the data
     * to HTTP request. Then JSP servlet will handle this request.
     *
     * @param request HTTP request
     */
    @Override
    public void prepareJspData(HttpServletRequest request) {
        CourseDao courses = appContext.getCourseDao();

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
                Course course = new Course();
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
                Course course = new Course();
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


    public String getFaculty() {
        return faculty;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
