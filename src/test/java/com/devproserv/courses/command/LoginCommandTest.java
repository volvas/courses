package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.dao.CourseDao;
import com.devproserv.courses.dao.UserDao;
import com.devproserv.courses.model.Student;
import com.devproserv.courses.model.User;
import com.devproserv.courses.model.User.Role;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;
import static com.devproserv.courses.config.MainConfig.LECTURER_PAGE;

/**
 * Contains unit-tests to check functionality of {@link LoginCommand} class
 * 
 * @author vovas11
 *
 */
public class LoginCommandTest {
    
    // dependencies to be mocked
    @Mock
    private AppContext appContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private UserDao userDao;
    @Mock
    private CourseDao courseDao;
    @Mock
    private User user;
    @Mock
    private Student student;
    
    private LoginCommand loginCommand;
    
    // prepare dependencies
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        loginCommand = new LoginCommand(appContext);
        // mocks methods of HttpServletRequest
        when(request.getParameter("login")).thenReturn("Login");
        when(request.getParameter("password")).thenReturn("Password");
        when(request.getSession()).thenReturn(session);
        doNothing().when(request).setAttribute(anyString(), anyString());
        // mocks methods of HttpSession
        doNothing().when(session).setAttribute(anyString(), any());
        // mocks methods of AppContext
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        // mocks methods of UserDao
        when(userDao.getUser("Login", "Password")).thenReturn(user);
        // mocks methods of CourseDao
        when(courseDao.getSubscribedCourses(user)).thenReturn(Collections.emptyList());
        when(courseDao.getAvailableCourses(user)).thenReturn(Collections.emptyList());
    }
    
    @Test
    public void testExecuteCommandOkStudent() {
        // mocks methods of UserDao
        when(userDao.userExists("Login", "Password")).thenReturn(Boolean.valueOf(true));
        // mocks methods of Student
        when(user.getRole()).thenReturn(Role.STUD);
        
        String page = loginCommand.executeCommand(request);
        assertEquals("Should be equal to " + STUDENT_PAGE, STUDENT_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandOkLecturer() {
        when(userDao.userExists("Login", "Password")).thenReturn(Boolean.valueOf(true));
        // mocks methods of Student
        when(user.getRole()).thenReturn(Role.LECT);
        
        String page = loginCommand.executeCommand(request);
        assertEquals("Should be equal to " + LECTURER_PAGE, LECTURER_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandOkAdmin() {
        when(userDao.userExists("Login", "Password")).thenReturn(Boolean.valueOf(true));
        // mocks methods of Student
        when(user.getRole()).thenReturn(Role.ADMIN);
        
        String page = loginCommand.executeCommand(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandInvalidLogin() {
        // mocks methods of UserDao
        when(request.getParameter("login")).thenReturn("contains space");
        when(request.getParameter("password")).thenReturn("Password");
        
        String page = loginCommand.executeCommand(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandInvalidPassword() {
        // mocks methods of HttpServletRequest
        when(request.getParameter("login")).thenReturn("Login");
        when(request.getParameter("password")).thenReturn("");
        
        String page = loginCommand.executeCommand(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandUserDoesNotExist() {
        // mocks methods of HttpServletRequest
        when(request.getParameter("login")).thenReturn("Login");
        when(request.getParameter("password")).thenReturn("Password");
        // mocks methods of UserDao
        when(userDao.userExists("Login", "Password")).thenReturn(Boolean.valueOf(false));
        
        String page = loginCommand.executeCommand(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
}
