package com.devproserv.courses.command;

import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.dao.CourseDao;
import com.devproserv.courses.dao.UserDao;
import com.devproserv.courses.model.User;
import com.devproserv.courses.model.User.Role;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static com.devproserv.courses.config.MainConfig.LECTURER_PAGE;
import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;
import static com.devproserv.courses.config.MainConfig.STUDENT_PAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Contains unit-tests to check functionality of {@link Login} class
 * 
 */
public class LoginTest {
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

    private Login login;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        login = new Login(appContext);

        when(request.getParameter("login")).thenReturn("Login");
        when(request.getParameter("password")).thenReturn("Password");
        when(request.getSession()).thenReturn(session);
        doNothing().when(request).setAttribute(anyString(), anyString());

        doNothing().when(session).setAttribute(anyString(), any());

        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getCourseDao()).thenReturn(courseDao);

        when(userDao.getUser("Login", "Password")).thenReturn(user);

        when(courseDao.getSubscribedCourses(user)).thenReturn(Collections.emptyList());
        when(courseDao.getAvailableCourses(user)).thenReturn(Collections.emptyList());
    }
    
    @Test
    public void testExecuteCommandOkStudent() {
        when(userDao.userExists("Login", "Password")).thenReturn(true);

        when(user.getRole()).thenReturn(Role.STUD);
        
        String page = login.path(request);
        assertEquals("Should be equal to " + STUDENT_PAGE, STUDENT_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandOkLecturer() {
        when(userDao.userExists("Login", "Password")).thenReturn(true);

        when(user.getRole()).thenReturn(Role.LECT);
        
        String page = login.path(request);
        assertEquals("Should be equal to " + LECTURER_PAGE, LECTURER_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandOkAdmin() {
        when(userDao.userExists("Login", "Password")).thenReturn(true);

        when(user.getRole()).thenReturn(Role.ADMIN);
        
        String page = login.path(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandInvalidLogin() {
        when(request.getParameter("login")).thenReturn("contains space");
        when(request.getParameter("password")).thenReturn("Password");
        
        String page = login.path(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandInvalidPassword() {
        when(request.getParameter("login")).thenReturn("Login");
        when(request.getParameter("password")).thenReturn("");
        
        String page = login.path(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandUserDoesNotExist() {
        when(request.getParameter("login")).thenReturn("Login");
        when(request.getParameter("password")).thenReturn("Password");

        when(userDao.userExists("Login", "Password")).thenReturn(false);
        
        String page = login.path(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
}
