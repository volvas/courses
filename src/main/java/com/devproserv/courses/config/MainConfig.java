package com.devproserv.courses.config;

/**
* Service class stores configuration of the application
* 
* @author vovas11
* @see CommandFactory
*/
public class MainConfig {
    
    /** Web page names */
    public static final String HOME_PAGE = "/index.html";
    public static final String NOT_FOUND_PAGE = "/404.html";
    public static final String GENERIC_ERR_PAGE = "/errorgen.html";
    public static final String EXCEPTION_ERR_PAGE = "/errorexcep.html";
    
    /** JSP names */
    public static final String SIGNUP_PAGE = "/signup.jsp";
    public static final String LOGIN_PAGE = "/login.jsp";
    public static final String STUDENT_PAGE = "/courses.jsp";
    public static final String LECTURER_PAGE = "/students.jsp";
    
    /** SQL statements that are used for CRUD operations */
    // users
    public final static String SELECT_USER_SQL = "SELECT * FROM users WHERE login=? AND password=?;";
    public final static String SELECT_LOGIN_SQL = "SELECT * FROM users WHERE login=?;";
    public final static String INSERT_USER_SQL = 
            "INSERT INTO users (firstname, lastname, login, password, role) VALUES(?, ?, ?, ?, ?);";
    
    public final static String INSERT_STUDENT_SQL = 
            "INSERT INTO students (stud_id, faculty) VALUES(?, ?);";
    
    public final static String GET_USER_FIELDS_SQL = 
              "SELECT user_id, firstname, lastname, faculty FROM users "
            + "JOIN students ON users.user_id = students.stud_id WHERE login = ?;";
    
    // courses
    public final static String SELECT_SUBSCR_COURSES_SQL = 
              "SELECT * FROM courses WHERE courses.course_id IN ("
            + "SELECT student_courses.course_id FROM student_courses, users "
            + "WHERE student_courses.stud_id = users.user_id AND users.login = ?);";
    
    public final static String SELECT_AVAIL_COURSES_SQL = 
              "SELECT * FROM courses WHERE course_id NOT IN ("
            + "SELECT course_id FROM student_courses WHERE stud_id IN ("
            + "SELECT user_id FROM users WHERE login = ?));";
    
    public final static String INSERT_USER_COURSES_SQL = 
              "INSERT INTO student_courses "
            + "(course_id, student_id, state) VALUES(?, (SELECT students.student_id "
            + "FROM students WHERE login = ?), 'STARTED');";
    
    public final static String DELETE_USER_COURSES_SQL = 
            "DELETE FROM student_courses WHERE course_id = ? AND student_id = ?;";
    
    
    // instance is not needed
    private MainConfig() {}
}
