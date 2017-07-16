package com.devproserv.courses.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static com.devproserv.courses.config.MainConfig.SELECT_LOGIN_SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devproserv.courses.controller.AppContext;
import com.devproserv.courses.model.Student;
import com.devproserv.courses.model.User;

/**
 * Contains unit-tests to check functionality of {@link UserDao} class
 * 
 * @author vovas11
 *
 */
public class UserDaoTest {
    // dependencies to be mocked
    @Mock
    private AppContext appContext;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement prepStmt;
    @Mock
    private ResultSet resultSet;
    @Mock
    private User user;
    @Mock
    private Student student;
    
    private UserDao userDao;
    
    // prepare dependencies
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userDao = new UserDao(connection);
        // mocks methods of Connection
        when(connection.prepareStatement(SELECT_LOGIN_SQL)).thenReturn(prepStmt);
        // mocks methods of PreparedStatement
        doNothing().when(prepStmt).setString(1, "Login");
        when(prepStmt.executeQuery()).thenReturn(resultSet);
    }
    
    @Test
    public void testLoginExistsOk() throws SQLException {
        // mocks methods of ResultSet
        when(resultSet.next()).thenReturn(Boolean.valueOf(false));
        
        Boolean loginExists = userDao.loginExists("Login");
        assertFalse("Should be false",loginExists);
    }
    
    @Test
    public void testLoginExistsNotOk() throws SQLException {
        // mocks methods of ResultSet
        when(resultSet.next()).thenReturn(Boolean.valueOf(true));
        
        Boolean loginExists = userDao.loginExists("Login");
        assertTrue("Should be false",loginExists);
    }
}
