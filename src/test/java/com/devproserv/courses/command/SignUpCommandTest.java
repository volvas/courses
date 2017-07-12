package com.devproserv.courses.command;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.dao.UserDao;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import static com.devproserv.courses.config.MainConfig.SIGNUP_PAGE;
import static com.devproserv.courses.config.MainConfig.LOGIN_PAGE;

/**
 * Contains unit-tests to check functionality of {@link SignUpCommand} class
 * 
 * @author vovas11
 *
 */
public class SignUpCommandTest {
    
    // dependencies to be mocked
    @Mock
    private AppContext appContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private UserDao userDao;
    
    private SignUpCommand signupCommand;
    
    // prepare dependencies
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        signupCommand = new SignUpCommand(appContext);
        // mocks methods of HttpServletRequest
        when(request.getParameter("login")).thenReturn("Login");
        when(request.getParameter("password")).thenReturn("Password");
        when(request.getParameter("firstname")).thenReturn("FirstName");
        when(request.getParameter("lastname")).thenReturn("LastName");
        when(request.getParameter("faculty")).thenReturn("Faculty");
        doNothing().when(request).setAttribute(eq("message"), anyString());
        // mocks methods of AppContext
        when(appContext.getUserDao()).thenReturn(userDao);
        // mocks methods of UserDao
        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(false));
        when(userDao.createUser("Login", "Password", "FirstName", "LastName", "Faculty"))
                .thenReturn(Boolean.valueOf(true));
    }
    
    @Test
    public void testExecuteCommandOk() {
        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(false));
        when(userDao.createUser("Login", "Password", "FirstName", "LastName", "Faculty"))
                .thenReturn(Boolean.valueOf(true));
        
        String page = signupCommand.executeCommand(request);
        assertEquals("Should be equal to " + LOGIN_PAGE, LOGIN_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandUserExists() {
        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(true));
        when(userDao.createUser("Login", "Password", "FirstName", "LastName", "Faculty"))
                .thenReturn(Boolean.valueOf(true));
        
        String page = signupCommand.executeCommand(request);
        assertEquals("Should be equal to " + SIGNUP_PAGE, SIGNUP_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandDBTroubles() {
        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(false));
        when(userDao.createUser("Login", "Password", "FirstName", "LastName", "Faculty"))
                .thenReturn(Boolean.valueOf(false));
        
        String page = signupCommand.executeCommand(request);
        assertEquals("Should be equal to " + SIGNUP_PAGE, SIGNUP_PAGE, page);
    }
    
    @Test
    public void testExecuteCommandWrongParameters() {
        when(request.getParameter("login")).thenReturn("");
        when(userDao.loginExists("Login")).thenReturn(Boolean.valueOf(false));
        when(userDao.createUser("", "Password", "FirstName", "LastName", "Faculty"))
                .thenReturn(Boolean.valueOf(true));
        
        String page = signupCommand.executeCommand(request);
        assertEquals("Should be equal to " + SIGNUP_PAGE, SIGNUP_PAGE, page);
    }

}
